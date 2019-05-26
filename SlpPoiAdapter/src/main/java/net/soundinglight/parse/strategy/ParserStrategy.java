/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import net.soundinglight.parse.header.SessionHeaderExtractor;

import javax.annotation.CheckForNull;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A strategy that determines how to parse a {@link net.soundinglight.poi.bo.Document} to an
 * {@link net.soundinglight.bo.Event}.
 */
public abstract class ParserStrategy {
    private static final int INDENT_THRESHOLD = 500;
    private static final Pattern PARAGRAPH_LABEL_PATTERN = Pattern.compile("^(?<label>\\d+|-)(?:\\s+(?<remainder>.*))?$");
    static final Pattern PARAGRAPH_LABEL_NO_DASH_PATTERN = Pattern.compile("^(?<label>\\d+)(?:\\s+(?<remainder>.*))?$");
    private static final Pattern TRACK_DURATION_PATTERN =
            Pattern.compile("^(?:(?<durationMin>\\d+) mins?|(?<durationSec>\\d+) secs?)\\.?(?:\\s.*)?$");

    private final SessionHeaderExtractor headerExtractor;
    private final SessionHeaderPatterns sessionHeaderPatterns;

    protected ParserStrategy(SessionHeaderPatterns sessionHeaderPatterns) {
        this(new SessionHeaderExtractor(), sessionHeaderPatterns);
    }

    protected ParserStrategy(SessionHeaderExtractor extractor, SessionHeaderPatterns sessionHeaderPatterns) {
        headerExtractor = extractor;
        this.sessionHeaderPatterns = sessionHeaderPatterns;
    }

    /**
     * @return the pattern that marks that a session in not available.
     */
    public final Pattern getSessionUnavailablePattern() {
        return sessionHeaderPatterns.getSessionUnavailablePattern();
    }

    /**
     * @return the pattern that marks the start of a session.
     */
    public final Pattern getSessionStartPattern() {
        return sessionHeaderPatterns.getSessionStartPattern();
    }

    /**
     * @return the patterns to parse the session header.
     */
    public final List<Pattern> getSessionHeaderPatterns() {
        return sessionHeaderPatterns.getHeaderLinePatterns();
    }

    /**
     * @return the pattern that marks the key topics line.
     */
    @CheckForNull
    public final Pattern getKeyTopicsPattern() {
        return sessionHeaderPatterns.getKeyTopicsPattern();
    }

    /**
     * @return the headerExtractor.
     */
    public SessionHeaderExtractor getHeaderExtractor() {
        return headerExtractor;
    }

    /**
     * @return the threshold for to match the paragraph indent or first line indent against to
     * determine whether the paragraph is to be marked indented.
     */
    public int getIndentThreshold() {
        return INDENT_THRESHOLD;
    }

    /**
     * @return the pattern to extract the paragraph label from its contents.
     */
    public Pattern getParagraphLabelPattern() {
        return PARAGRAPH_LABEL_PATTERN;
    }

    /**
     * @return the pattern to determine track length (in mins) indication.
     */
    public Pattern getTrackDurationPattern() {
        return TRACK_DURATION_PATTERN;
    }
}
