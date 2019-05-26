/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import net.soundinglight.bo.Event;
import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.bo.SlpSession;
import net.soundinglight.input.PoiInputException;
import net.soundinglight.parse.content.ParagraphConverter;
import net.soundinglight.parse.strategy.ParserStrategy;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.poi.bo.Section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parses a {@link net.soundinglight.poi.bo.Document} to an {@link net.soundinglight.bo.Event}.
 */
public class SlpParser {
    private static final String SESSION_END_MARKER = "***";
    private final ParserStrategy strategy;

    /**
     * C'tor.
     *
     * @param strategy the {@link ParserStrategy} to use.
     */
    public SlpParser(ParserStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Parse a tapelist.
     *
     * @param source       the {@link SlpParserSource} that provides a {@link Document}.
     * @param target       the {@link SlpParserTarget} that receives the resulting {@link Event}.
     * @param tapelistYear the year of the parsed tapelist.
     * @throws ParseException on failure.
     */
    public void parse(SlpParserSource source, SlpParserTarget target, int tapelistYear) throws ParseException {
        Document document;
        try {
            document = source.getDocument();
        } catch (PoiInputException e) {
            throw new ParseException("failed to load the POI document", e);
        }

        List<Section> sections = splitInSections(document);
        List<SlpParagraph> preamble = checkAndExtractSection(sections, 0);
        List<SlpParagraph> conclusion = checkAndExtractSection(sections, sections.size() - 1);
        List<SlpSession> sessions = new SectionParser(strategy, tapelistYear).parseSections(sections);
        target.setEvent(new Event(document.getName(), preamble, conclusion, sessions));
    }

    private List<SlpParagraph> checkAndExtractSection(List<Section> sections, int index) {
        if (sectionContainsSessionStart(sections.get(index))) {
            return Collections.emptyList();
        }

        return new ParagraphConverter(strategy).convert(sections.remove(index),
                ParagraphConverter.Mode.PREAMBLE_OR_CONCLUSION);
    }

    private List<Section> splitInSections(Document document) {
        List<Section> sections = new ArrayList<>();

        List<Paragraph> paragraphs = document.getParagraphs();
        int from = 0;
        for (int i = 0; i < paragraphs.size(); i++) {
            String text = paragraphs.get(i).getText();

            if ("FYA".equals(text)) {
                break;
            }

            if (strategy.getSessionStartPattern().matcher(text).matches() && i > from) {
                addSectionIfContainsNonWhitespaceContent(sections, extractSection(paragraphs, from, i));
                from = i;
            }

            if (SESSION_END_MARKER.equals(text)) {
                addSectionIfContainsNonWhitespaceContent(sections, extractSection(paragraphs, from, i + 1));
                from = i + 1;
            }
        }

        if (from <= paragraphs.size() - 1) {
            addSectionIfContainsNonWhitespaceContent(sections, extractSection(paragraphs, from, paragraphs.size()));
        }

        return sections;
    }

    private Section extractSection(List<Paragraph> paragraphs, int from, int i) {
        return new Section(paragraphs.subList(from, i));
    }

    private void addSectionIfContainsNonWhitespaceContent(List<Section> sections, Section section) {
        if (hasNonWhitespaceContent(section)) {
            sections.add(new Section(section));
        }
    }

    private boolean hasNonWhitespaceContent(Section section) {
        for (Paragraph paragraph : section) {
            if (!paragraph.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean sectionContainsSessionStart(Section section) {
        return strategy.getSessionStartPattern().matcher(section.get(0).getText()).matches();
    }
}
