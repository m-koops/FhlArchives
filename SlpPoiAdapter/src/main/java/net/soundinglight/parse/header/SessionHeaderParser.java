/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.header;

import net.soundinglight.bo.*;
import net.soundinglight.parse.HeaderParserException;
import net.soundinglight.parse.ParseException;
import net.soundinglight.parse.strategy.ParserStrategy;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parses the metadata header of a session.
 */
public class SessionHeaderParser {
    private static final float TITLE_MIN_UPPERCASE_FRACTION = 0.7f;
    private final ParserStrategy strategy;
    private final SessionHeaderDetailsCollector collector;

    /**
     * C'tor.
     *
     * @param strategy     the {@link ParserStrategy} to use.
     * @param tapelistYear the year of the tapelist being parsed.
     */
    public SessionHeaderParser(ParserStrategy strategy, int tapelistYear) {
        this.strategy = strategy;
        collector = new SessionHeaderDetailsCollector(tapelistYear);
    }

    /**
     * Parse the header of a session to extract the {@link SessionDetails}.
     *
     * @param headerParagraphs the lines of the header of the session.
     * @return the resulting {@link SessionDetails}.
     * @throws HeaderParserException on failure.
     */
    public SessionDetails parseSessionHeader(List<SlpParagraph> headerParagraphs) throws HeaderParserException {
        if (headerParagraphs.isEmpty()) {
            throw new HeaderParserException("no header lines found", Collections.emptyList(), Collections.emptyList());
        }
        return parseHeaderLines(headerParagraphs);
    }

    private SessionDetails parseHeaderLines(List<SlpParagraph> headerParagraphs) throws HeaderParserException {
        if (strategy.getKeyTopicsPattern() != null) {
            extractAndParseKeyTopics(headerParagraphs);
        }

        List<SlpParagraph> titleParagraphs = extractTitles(headerParagraphs);
        parseTitles(titleParagraphs);

        List<Pattern> patterns = new ArrayList<>(strategy.getSessionHeaderPatterns());
        for (SlpParagraph paragraph : headerParagraphs) {
            if (!paragraph.isEmpty()) {
                matchToAnyAvailablePattern(paragraph, patterns);
            }
        }

        try {
            return collector.createSessionDetails();
        } catch (ParseException e) {
            throw new HeaderParserException(e.getMessage(), headerParagraphs, patterns, e);
        }
    }

    private void matchToAnyAvailablePattern(SlpParagraph paragraph, List<Pattern> patterns) {
        String text = paragraph.asPlainText().trim();

        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(text);
            if (matcher.matches()) {
                collector.collectDetailsFromMatcher(matcher);
                return;
            }
        }
    }

    private void extractAndParseKeyTopics(List<SlpParagraph> headerParagraphs) {
        Pattern keyTopicsPattern = Objects.requireNonNull(strategy.getKeyTopicsPattern());
        StringBuilder keyTopicsLine = new StringBuilder();

        ListIterator<SlpParagraph> it = headerParagraphs.listIterator();
        while (it.hasNext()) {
            String line = it.next().asPlainText().trim();
            if (keyTopicsPattern.matcher(line).matches()) {
                keyTopicsLine.append(line);
                it.remove();
                break;
            }
        }

        while (it.hasNext()) {
            String line = it.next().asPlainText().trim();
            if (line.isEmpty()) {
                break;
            }

            keyTopicsLine.append(" ").append(line);
            it.remove();
        }

        Matcher matcher = keyTopicsPattern.matcher(keyTopicsLine.toString());
        if (matcher.matches()) {
            collector.collectDetailsFromMatcher(matcher);
        }
    }

    private List<SlpParagraph> extractTitles(List<SlpParagraph> headerParagraphs) {
        List<SlpParagraph> titleParagraphs = new ArrayList<>();
        Iterator<SlpParagraph> it = headerParagraphs.iterator();

        while ((it.hasNext())) {
            SlpParagraph paragraph = it.next();

            if (paragraph.isEmpty()) {
                continue;
            }

            if (isMainTitleParagraph(paragraph)) {
                titleParagraphs.add(paragraph);
                it.remove();
                break;
            }
        }

        while (it.hasNext()) {
            SlpParagraph paragraph = it.next();


            if (paragraph.isEmpty()) {
                continue;
            }

            if (paragraph.getAlignment() != Alignment.CENTER) {
                break;
            }

            titleParagraphs.add(paragraph);
            it.remove();
        }

        return titleParagraphs;
    }

    private boolean isMainTitleParagraph(SlpParagraph paragraph) {
        if (paragraph.getAlignment() != Alignment.CENTER) {
            return false;
        }

        String text = paragraph.asPlainText();
        int charCount = text.length();
        long uppercaseCharCount = paragraph.getElements()
                .stream()
                .filter(SlpParagraphElement::isTextFragment)
                .map(SlpParagraphElement::asTextFragment)
                .map(this::countUppercaseCharsInFragment)
                .mapToInt(Integer::intValue)
                .sum();;
         return ((float) uppercaseCharCount / charCount) > TITLE_MIN_UPPERCASE_FRACTION;
    }

    private int countUppercaseCharsInFragment(TextFragment fragment) {
        if (fragment.getType() != TextType.LATIN) {
            return fragment.getText().length();
        }

        return (int) fragment.getText()
                .codePoints()
                .mapToObj(c -> (char) c)
                .filter(ch -> ch == Character.toUpperCase(ch))
                .count();
    }

    private void parseTitles(List<SlpParagraph> titleParagraphs) {
        titleParagraphs.removeIf(SlpParagraph::isEmpty);

        if (!titleParagraphs.isEmpty()) {
            collector.addValue("title", titleParagraphs.remove(0).asPlainText().trim());
        }

        String subTitle = titleParagraphs.stream()
                .map(paragraph -> paragraph.asPlainText().trim())
                .collect(Collectors.joining(" "));
        if (!subTitle.isEmpty()) {
            collector.addValue("subTitle", subTitle);
        }
    }
}
