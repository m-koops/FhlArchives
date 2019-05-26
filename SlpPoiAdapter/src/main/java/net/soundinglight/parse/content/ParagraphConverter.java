/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.content;

import net.soundinglight.bo.Alignment;
import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.bo.SlpParagraphElement;
import net.soundinglight.bo.TextFragment;
import net.soundinglight.parse.strategy.ParserStrategy;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.poi.bo.TextAlignment;
import net.soundinglight.util.StringUtil;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converter to transform {@link Paragraph} into {@link SlpParagraph}.
 */
public class ParagraphConverter {
    public static enum Mode {
        SESSION, PREAMBLE_OR_CONCLUSION
    }

    private static final Pattern BULLET_LABEL_PATTERN = Pattern.compile(
            "^(?<bullet>\\s?\\d+|A|E|Q)\\s+(?:\\.|\\(|\\p{Upper}).*$");
    private final ParserStrategy strategy;

    public ParagraphConverter(ParserStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Converts a {@link List} of {@link Paragraph}s into {@link SlpParagraph}s.
     *
     * @param paragraphs the {@link Paragraph}s to convert.
     * @param mode       the conversion mode to apply.
     * @return the resulting {@link SlpParagraph}s.
     */
    public List<SlpParagraph> convert(List<Paragraph> paragraphs, Mode mode) {
        List<SlpParagraph> slpParagraphs = new ArrayList<>();
        for (Paragraph poiParagraph : paragraphs) {
            slpParagraphs.add(convert(poiParagraph, mode));
        }
        return slpParagraphs;
    }

    /**
     * Converts a {@link Paragraph} into {@link SlpParagraph}.
     *
     * @param paragraph the {@link Paragraph} to convert.
     * @param mode      the conversion mode to apply.
     * @return the resulting {@link SlpParagraph}.
     */
    SlpParagraph convert(Paragraph paragraph, Mode mode) {
        List<SlpParagraphElement> elements = ParagraphElementConverter.convert(paragraph.getElements());
        TextAlignment textAlignment = paragraph.getTextAlignment();

        String paragraphLabel = null;
        String bulletLabel = null;
        if (mode == Mode.SESSION) {
            boolean containsImage = elements.stream().anyMatch(SlpParagraphElement::isImage);
            boolean isTextParagraph = !elements.isEmpty() && !containsImage;
            boolean isNonIndented = (textAlignment == TextAlignment.LEFT || textAlignment == TextAlignment.JUSTIFY) && paragraph
                    .getFirstLineIndent() <= paragraph.getIndentFromLeft();
            if (isTextParagraph && isNonIndented && !isTrackDurationParagraph(elements)) {
                if (paragraph.getFirstLineIndent() <= 0) {
                    paragraphLabel = extractParagraphLabelFromElements(elements);
                }
                bulletLabel = extractBulletLabelFromElements(elements);
            }
        }
        List<? extends SlpParagraphElement> sanitized = new TextFragmentsSanitizer(strategy).sanatize(elements);

        return new SlpParagraph(paragraph.getId(), sanitized, convertAlignment(textAlignment), isIndented(paragraph),
                paragraphLabel, bulletLabel);
    }

    private boolean isTrackDurationParagraph(List<SlpParagraphElement> elements) {
        return strategy.getTrackDurationPattern().matcher(getParagraphText(elements)).matches();
    }

    @CheckForNull
    private String extractParagraphLabelFromElements(List<SlpParagraphElement> elements) {
        TextFragment firstFragment = elements.get(0).asTextFragment();
        Pattern paragraphLabelPattern = strategy.getParagraphLabelPattern();
        Matcher matcher = paragraphLabelPattern.matcher(firstFragment.getText());
        if (!matcher.matches()) {
            return null;
        }

        String remainder = StringUtil.emptyIfNull(matcher.group("remainder"));
        if (remainder.isEmpty()) {
            elements.remove(0);
        } else {
            elements.set(0, new TextFragment(firstFragment.getType(), firstFragment.getStyle(), remainder));
        }

        return matcher.group("label");
    }

    @CheckForNull
    private String extractBulletLabelFromElements(List<SlpParagraphElement> elements) {
        String paragraphText = getParagraphText(elements);
        Matcher matcher = BULLET_LABEL_PATTERN.matcher(paragraphText);
        if (!matcher.matches()) {
            return null;
        }

        String bulletLabel = matcher.group("bullet");
        String result = bulletLabel.trim();

        for (int i = 0; !StringUtil.isNullOrEmpty(bulletLabel); i++) {
            TextFragment textFragment = elements.get(i).asTextFragment();
            String text = textFragment.getText();

            if (text.startsWith(bulletLabel)) {
                text = text.substring(bulletLabel.length());
                bulletLabel = null;
            } else if (bulletLabel.startsWith(text)) {
                bulletLabel = bulletLabel.substring(text.length());
                text = "";
            }

            elements.set(i, new TextFragment(textFragment.getType(), textFragment.getStyle(), text));
        }

        return result;
    }

    private String getParagraphText(List<SlpParagraphElement> elements) {
        StringBuilder builder = new StringBuilder();

        for (SlpParagraphElement element : elements) {
            if (element.isTextFragment()) {
                builder.append(element.asTextFragment().getText());
            }
        }

        return builder.toString();
    }

    private Alignment convertAlignment(TextAlignment textAlignment) {
        return Alignment.valueOf(textAlignment.name());
    }

    private boolean isIndented(Paragraph paragraph) {
        int threshold = strategy.getIndentThreshold();
        return paragraph.getIndentFromLeft() > threshold || paragraph.getFirstLineIndent() > threshold;
    }
}
