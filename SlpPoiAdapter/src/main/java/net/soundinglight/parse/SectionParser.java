/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import net.soundinglight.bo.SessionDetails;
import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.bo.SlpSession;
import net.soundinglight.bo.Track;
import net.soundinglight.parse.content.ParagraphConverter;
import net.soundinglight.parse.content.TrackParser;
import net.soundinglight.parse.header.SessionHeaderParser;
import net.soundinglight.parse.strategy.ParserStrategy;
import net.soundinglight.poi.bo.Section;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 * Parses a {@link Section}s into {@link SlpSession}.
 */
public class SectionParser {
    private static final int MAX_IGNOREABLE_SECTION_SIZE = 5;
    private static final Logger LOG = Logger.getLogger(SectionParser.class.getName());
    private final ParserStrategy strategy;
    private int tapelistYear;

    /**
     * C'tor.
     *
     * @param strategy     the {@link ParserStrategy} to use.
     * @param tapelistYear the year of the tapelist being parsed.
     */
    public SectionParser(ParserStrategy strategy, int tapelistYear) {
        this.strategy = strategy;
        this.tapelistYear = tapelistYear;
    }

    /**
     * parse {@link Section}s into {@link SlpSession}s.
     *
     * @param sections the sections to parse.
     * @return the resulting {@link SlpSession}s.
     * @throws ParseException on failure.
     */
    public List<SlpSession> parseSections(List<Section> sections) throws ParseException {
        List<SlpSession> sessions = new ArrayList<>();

        for (Section section : sections) {
            SlpSession session = parseSession(section);
            if (session != null) {
                sessions.add(session);
            }
        }

        return sessions;
    }

    @CheckForNull
    private SlpSession parseSession(Section section) throws ParseException {
        List<SlpParagraph> paragraphs = new ParagraphConverter(strategy).convert(section,
                ParagraphConverter.Mode.SESSION);

        List<SlpParagraph> headerParagraphs = strategy.getHeaderExtractor().extractHeader(paragraphs);
        assert headerParagraphs.size() > 0 : "empty section header not expected";

        Matcher sessionUnavailableMatcher = strategy.getSessionUnavailablePattern()
                .matcher(headerParagraphs.get(0).asPlainText());
        if (sessionUnavailableMatcher.matches()) {
            String id = sessionUnavailableMatcher.group("id");
            return new SlpSession(SessionDetails.builder(id).setAvailable(false).build(),
                    Collections.<Track>emptyList(), Collections.<String>emptyList());
        }

        if (paragraphs.isEmpty() && headerParagraphs.size() <= MAX_IGNOREABLE_SECTION_SIZE) {
            String strategyName = strategy.getClass().getSimpleName();
            LOG.warning(
                    "Ignore session as it only contains header lines " + headerParagraphs + " (using strategy " + strategyName + ")");

            return null;
        }

        SessionHeaderParser headerParser = new SessionHeaderParser(strategy, tapelistYear);
        SessionDetails sessionDetails = headerParser.parseSessionHeader(headerParagraphs);

        List<Track> tracks = new TrackParser(strategy).parse(paragraphs);

        String strategyName = strategy.getClass().getSimpleName();
        LOG.info("Completed parsing of session " + sessionDetails.getId() + " using strategy " + strategyName);

        return new SlpSession(sessionDetails, tracks, Collections.<String>emptyList());
    }
}
