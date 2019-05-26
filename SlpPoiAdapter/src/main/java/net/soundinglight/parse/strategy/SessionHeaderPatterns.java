/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import net.soundinglight.UnicodeConstants;
import net.soundinglight.util.StringUtil;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The patterns used to parse session headers.
 */
public final class SessionHeaderPatterns {
    private static final String ANY = ".";
    private static final String PLUS = "\\+";
    private static final String DASH = "-";
    private static final String TAB = "\\t";
    private static final String BRACKET_CLOSE = "\\)";
    private static final String BRACKET_OPEN = "\\(";
    private static final String SQR_ROOT = "" + UnicodeConstants.SQR_ROOT;
    private static final String WORD = "\\w";
    private static final String DOT = "\\.";
    private static final String ALPHA = "\\p{Alpha}";
    private static final String DIGIT = "\\d";
    private static final String SPACE = " ";
    private static final String WSPACE = "\\s";
    private static final String NON_WSPACE = "\\S";
    private static final String NOT_TAB = notChar(TAB);

    private static final String OPT_DOT = opt(DOT);
    private static final String OPT_DASH = opt(DASH);
    private static final String OPT_WSPACE = opt(WSPACE);
    private static final String MULT_ANY = mult(ANY);
    private static final String MULTI_SPACE = mult(SPACE);
    private static final String MULTI_WSPACE = mult(WSPACE);
    private static final String MULTI_TAB = mult(TAB);
    private static final String MULTI_DIGIT = mult(DIGIT);
    private static final String MULTI_ALPHA = mult(ALPHA);
    private static final String MULTI_WORD = mult(WORD);
    private static final String MULTI_NON_WSPACE = mult(NON_WSPACE);
    private static final String ZOM_WSPACE = zom(WSPACE);
    private static final String ZOM_TAB = zom(TAB);
    private static final String ZOM_NOT_TAB = zom(NOT_TAB);
    private static final String OPT_TEXT_WITH_SPACE = zomChars(NON_WSPACE, SPACE);
    private static final String OPT_TEXT_WITH_WSPACE = zomChars(NON_WSPACE, WSPACE);
    private static final String TEXT_WITH_SPACE = multChars(NON_WSPACE, SPACE);

    private static final String ID_SUFFIX = optGroup(WSPACE, BRACKET_OPEN, DIGIT, zom(nonCapt(or(" & ", ", "), DIGIT)),
            BRACKET_CLOSE);
    private static final String ID_GROUP = capt("id", MULTI_DIGIT, ID_SUFFIX);
    private static final String OPT_ID_GROUP = opt(ID_GROUP);
    private static final String RESTRICTION_GROUP = nonCapt(OPT_DASH, OPT_WSPACE, capt("restriction", TEXT_WITH_SPACE));

    private static final String OPT_DAY_GROUP = optGroup(capt("day", MULTI_WORD, OPT_DOT), opt(","), MULTI_WSPACE);
    private static final String CAPT_DATE_DAY_PART = join(capt("date", rep(DIGIT, 1, 2)),
            opt(or("st", "nd", "rd", "th")));
    private static final String NONCAPT_DATE_DAY_PART = join(rep(DIGIT, 1, 2), opt(or("st", "nd", "rd", "th")));
    private static final String DATE_MONTH_PART = capt("month", MULTI_ALPHA, OPT_DOT);
    private static final String DATE_YEAR_PART = capt("year", rep(DIGIT, 4));
    private static final String DATE_GROUP = join(CAPT_DATE_DAY_PART, optGroup(DASH, NONCAPT_DATE_DAY_PART), WSPACE,
            DATE_MONTH_PART, optGroup(opt(","), OPT_WSPACE, DATE_YEAR_PART));
    private static final String MOMENTOFDAY_GROUP = capt("momentOfDay", TEXT_WITH_SPACE, " Session");
    private static final String OPT_MOMENT_OFDAY_GROUP = opt(MOMENTOFDAY_GROUP);
    private static final String LOCATION_GROUP = capt("location",
            notChar(WSPACE, BRACKET_OPEN, BRACKET_CLOSE, SQR_ROOT), OPT_TEXT_WITH_SPACE);
    private static final String OPT_LOCATION_GROUP = opt(LOCATION_GROUP);

    private static final String SIDE_DURATION_PREFIX = join(MULTI_WSPACE, optGroup(DASH, MULTI_WSPACE));
    private static final String SIDE_DURATION_SUFFIX = join(OPT_WSPACE, "min", opt("s"), DOT, ZOM_WSPACE);
    private static final String SIDE_OR_TAPE = or("Side", "Tape");
    private static final String SIDE_A_DURATION_GROUP = nonCapt(ZOM_WSPACE, SIDE_OR_TAPE + " A", opt(":"),
            SIDE_DURATION_PREFIX, or(join(capt("durationSideA", rep(DIGIT, 1, 2)), SIDE_DURATION_SUFFIX), "Blank"));
    private static final String SIDE_B_DURATION_GROUP = nonCapt(ZOM_WSPACE, SIDE_OR_TAPE + " B", opt(":"),
            SIDE_DURATION_PREFIX, or(join(capt("durationSideB", rep(DIGIT, 1, 2)), SIDE_DURATION_SUFFIX), "Blank"));

    private static final String CONTAINS_RESTRICTION = join(reluctant(ZOM_NOT_TAB),
            or(join(chars("R", "r"), "estrict"), "SR", "TUR", "TR"), ZOM_NOT_TAB);
    private static final String OPT_SUB_RESTRICTION_SIDE_A_B_GROUP = opt(capt("subRestriction",
            or(join(BRACKET_OPEN, opt(CONTAINS_RESTRICTION), BRACKET_CLOSE),
                    join("Side", SPACE, WORD, TAB, DASH, TAB, CONTAINS_RESTRICTION))));
    private static final String OPT_DURATION_GROUP = optGroup("Time:" + MULTI_WSPACE, capt("duration", MULTI_DIGIT),
            SPACE + "mins" + DOT);
    private static final String ADDITIONAL_REMARKS_GROUP = capt("additionalRemarks", NON_WSPACE,
            reluctant(OPT_TEXT_WITH_WSPACE));
    private static final String OPT_ADDITIONAL_REMARKS_GROUP = opt(ADDITIONAL_REMARKS_GROUP);
    private static final String OPT_RECOMMENDATIONS_GROUP = opt(capt("recommendation", mult(SQR_ROOT), opt(PLUS)));

    private static final String MEDIA_NR = join(rep(DIGIT, 1, 3), opt(ALPHA));
    private static final String VIDEO_GROUP = nonCapt("Video", MULTI_WSPACE, capt("video", MEDIA_NR));
    private static final String OPT_VIDEO_RESTRICTION_GROUP = optGroup(OPT_DASH, ZOM_WSPACE,
            capt("videoRestriction", TEXT_WITH_SPACE));
    private static final String PAL_GROUP = nonCapt("PAL", MULTI_WSPACE, or(capt("pal", MEDIA_NR), TEXT_WITH_SPACE));
    private static final String OPT_PAL_RESTRICTION_GROUP = optGroup(OPT_DASH, ZOM_WSPACE,
            capt("palRestriction", TEXT_WITH_SPACE));
    private static final String NTSC_GROUP = nonCapt("NTSC", MULTI_WSPACE, or(capt("ntsc", MEDIA_NR), TEXT_WITH_SPACE));
    private static final String OPT_NTSC_RESTRICTION_GROUP = optGroup(OPT_DASH, ZOM_WSPACE,
            capt("ntscRestriction", TEXT_WITH_SPACE));
    private static final String NOTES_GROUP = join(or("Notes?", "Reference"), opt(WSPACE), or(":", "-"), WSPACE,
            capt("notes", MULT_ANY));

    private static final Pattern PATTERN_1988_LINE_2 = compilePattern(SIDE_A_DURATION_GROUP,
            OPT_SUB_RESTRICTION_SIDE_A_B_GROUP, ZOM_WSPACE, OPT_MOMENT_OFDAY_GROUP, reluctant(ZOM_TAB),
            OPT_LOCATION_GROUP);
    private static final Pattern PATTERN_1991_LINE_2 = compilePattern(SIDE_A_DURATION_GROUP, ZOM_WSPACE,
            OPT_SUB_RESTRICTION_SIDE_A_B_GROUP, ZOM_WSPACE, OPT_MOMENT_OFDAY_GROUP, ZOM_WSPACE, OPT_LOCATION_GROUP);
    private static final Pattern PATTERN_1994_LINE_2 = compilePattern(SIDE_A_DURATION_GROUP,
            OPT_SUB_RESTRICTION_SIDE_A_B_GROUP, ZOM_WSPACE,
            optGroup(opt(BRACKET_OPEN), MOMENTOFDAY_GROUP, opt(BRACKET_CLOSE)), reluctant(ZOM_WSPACE),
            reluctant(OPT_LOCATION_GROUP), opt(","));
    private static final Pattern PATTERN_1994_SESSION_1777_LINE_2 = compilePattern(SIDE_A_DURATION_GROUP,
            OPT_SUB_RESTRICTION_SIDE_A_B_GROUP, reluctant(ZOM_WSPACE), MOMENTOFDAY_GROUP, LOCATION_GROUP, "{0}");
    private static final Pattern PATTERN_2004_LINE_2 = compilePattern(reluctant(OPT_SUB_RESTRICTION_SIDE_A_B_GROUP),
            ZOM_WSPACE, OPT_MOMENT_OFDAY_GROUP, ZOM_WSPACE, reluctant(OPT_LOCATION_GROUP));
    private static final Pattern PATTERN_2005_LINE_2 = compilePattern(OPT_SUB_RESTRICTION_SIDE_A_B_GROUP, ZOM_WSPACE,
            reluctant(OPT_MOMENT_OFDAY_GROUP), ZOM_WSPACE, OPT_LOCATION_GROUP);

    private static final Pattern PATTERN_1988_LINE_3 = compilePattern(SIDE_B_DURATION_GROUP,
            OPT_SUB_RESTRICTION_SIDE_A_B_GROUP, ZOM_TAB, OPT_ADDITIONAL_REMARKS_GROUP,
            optGroup(MULTI_TAB, MULTI_NON_WSPACE));
    private static final Pattern PATTERN_1991_LINE_3 = compilePattern(SIDE_B_DURATION_GROUP, ZOM_TAB,
            OPT_ADDITIONAL_REMARKS_GROUP);
    private static final Pattern PATTERN_2002_LINE_3 = compilePattern(SIDE_B_DURATION_GROUP,
            OPT_SUB_RESTRICTION_SIDE_A_B_GROUP, ZOM_TAB, OPT_RECOMMENDATIONS_GROUP, OPT_LOCATION_GROUP);
    private static final Pattern PATTERN_2004_LINE_3 = compilePattern(OPT_DURATION_GROUP, ZOM_WSPACE,
            reluctant(OPT_ADDITIONAL_REMARKS_GROUP), ZOM_WSPACE, OPT_RECOMMENDATIONS_GROUP);

    private static final Pattern PATTERN_1988_LINE_4 = compilePattern(VIDEO_GROUP, MULTI_WSPACE,
            reluctant(OPT_ADDITIONAL_REMARKS_GROUP), ZOM_WSPACE, OPT_RECOMMENDATIONS_GROUP);
    private static final Pattern PATTERN_1991_LINE_4 = compilePattern(PAL_GROUP, MULTI_WSPACE,
            OPT_PAL_RESTRICTION_GROUP, ZOM_WSPACE, OPT_ADDITIONAL_REMARKS_GROUP);
    private static final Pattern PATTERN_1994_LINE_4 = compilePattern(PAL_GROUP, MULTI_WSPACE,
            OPT_PAL_RESTRICTION_GROUP);
    private static final Pattern PATTERN_1991_LINE_5 = compilePattern(NTSC_GROUP, MULTI_WSPACE,
            OPT_NTSC_RESTRICTION_GROUP, ZOM_WSPACE, OPT_ADDITIONAL_REMARKS_GROUP);
    private static final Pattern PATTERN_1994_LINE_5 = compilePattern(NTSC_GROUP, MULTI_WSPACE,
            OPT_NTSC_RESTRICTION_GROUP);
    private static final Pattern PATTERN_1995_LINE_6 = compilePattern(
            or(nonCapt(optGroup("Video", MULTI_WSPACE), "No Video"),
                    nonCapt(VIDEO_GROUP, MULTI_WSPACE, OPT_VIDEO_RESTRICTION_GROUP)), ZOM_WSPACE,
            OPT_RECOMMENDATIONS_GROUP);

    private final Pattern sessionStartPattern;
    private final Pattern sessionUnavailablePattern;
    @CheckForNull
    private final Pattern keyTopicsPattern;
    private final List<Pattern> headerLinePatterns = new ArrayList<>();

    private SessionHeaderPatterns(String sessionMarker, String keyTopicsMarker) {
        String sessionMarkerPart = join(ZOM_WSPACE, sessionMarker, SPACE, ID_GROUP);
        this.sessionStartPattern = compilePattern(sessionMarkerPart, reluctant(MULT_ANY));
        this.sessionUnavailablePattern = compilePattern(sessionMarkerPart, optGroup(WSPACE, DASH), SPACE,
                optGroup("is "), or("un", join(chars("n", "N"), "ot ")), "available");
        this.keyTopicsPattern = keyTopicsMarker == null ? null : compilePattern(keyTopicsMarker, ":", OPT_WSPACE,
                opt(capt("keyTopics", reluctant(MULT_ANY))),
                optGroup(DOT, ZOM_WSPACE, opt(capt("author", MULTI_NON_WSPACE))));
        this.headerLinePatterns.add(compilePattern("Additional remarks: ", ADDITIONAL_REMARKS_GROUP));
        this.headerLinePatterns.add(compilePattern(NOTES_GROUP));
    }

    private static String or(String... options) {
        return nonCapt(StringUtil.join(Arrays.asList(options), "|"));
    }

    /**
     * reluctant qualifier.
     */
    private static String reluctant(String patternPart) {
        return join(patternPart, "?");
    }

    /**
     * zero or more of character in set.
     */
    private static String zomChars(String... chars) {
        return zom(chars(chars));
    }

    /**
     * zero or more of character in set.
     */
    private static String multChars(String... chars) {
        return mult(chars(chars));
    }

    /**
     * character in set.
     */
    private static String chars(String... chars) {
        return join("[", join(chars), "]");
    }

    /**
     * NOT character in set.
     */
    private static String notChar(String... chars) {
        return join("[^", join(chars), "]");
    }

    /**
     * zero or more.
     */
    private static String zom(String patternPart) {
        return join(patternPart, "*");
    }

    /**
     * repetition {x}.
     */
    private static String rep(String patternPart, int repetition) {
        return join(patternPart, "{" + repetition + "}");
    }

    /**
     * repetition {x, y}.
     */
    private static String rep(String patternPart, int min, int max) {
        return join(patternPart, "{" + min + "," + max + "}");
    }

    /**
     * multi (1 or more).
     */
    private static String mult(String patternPart) {
        return join(patternPart, "+");
    }

    /**
     * optional non-capturing group.
     */
    private static String optGroup(String... patternParts) {
        return opt(nonCapt(patternParts));
    }

    /**
     * optional.
     */
    private static String opt(String patternPart) {
        return join(patternPart, "?");
    }

    /**
     * non-capturing group.
     */
    private static String nonCapt(String... patternParts) {
        return join("(?:", join(patternParts), ")");
    }

    private static String join(String... patternParts) {
        return StringUtil.join(Arrays.asList(patternParts), "");
    }

    /**
     * capturing group.
     */
    private static String capt(String groupId, String... patternParts) {
        return join("(?<", groupId, ">", join(patternParts), ")");
    }

    /**
     * @return the header patterns for sessions starting from 1988.
     */
    public static SessionHeaderPatterns create1988Patterns() {
        SessionHeaderPatterns patterns = new SessionHeaderPatterns("Tape", "Keywords");
        patterns.addLinePattern(
                compilePattern("Tape" + MULTI_SPACE, ID_GROUP, MULTI_TAB, opt(RESTRICTION_GROUP), MULTI_TAB,
                        OPT_DAY_GROUP, DATE_GROUP));
        patterns.addLinePattern(PATTERN_1988_LINE_2);
        patterns.addLinePattern(PATTERN_1988_LINE_3);
        patterns.addLinePattern(PATTERN_1988_LINE_4);
        return patterns;
    }

    /**
     * @return the header patterns for sessions of 1991.
     */
    public static SessionHeaderPatterns create1991SessionPatternsWithKeyTopics() {
        return create1991Patterns("Session", "Key Topics");
    }

    /**
     * @return the header patterns for sessions of 1991.
     */
    public static SessionHeaderPatterns create1991SessionPatternsWithKeyWords() {
        return create1991Patterns("Session", "Keywords");
    }

    /**
     * @return the header patterns for sessions of 1991 for which no key topics are defined in the
     * session headers.
     */
    public static SessionHeaderPatterns create1991SessionPatternsWithNoKeyTopics() {
        return create1991Patterns("Session", null);
    }

    /**
     * @return the header patterns for Tape sessions of 1991.
     */
    public static SessionHeaderPatterns create1991TapePatternsWithKeyWords() {
        return create1991Patterns("Tape", "Keywords");
    }

    private static SessionHeaderPatterns create1991Patterns(String sessionMarker, String keyTopicsMarker) {
        SessionHeaderPatterns patterns = new SessionHeaderPatterns(sessionMarker, keyTopicsMarker);
        String remainder = optGroup(MULTI_TAB, RESTRICTION_GROUP, optGroup(MULTI_WSPACE, OPT_DAY_GROUP, DATE_GROUP));
        patterns.addLinePattern(
                compilePattern(sessionMarker + MULTI_SPACE, OPT_ID_GROUP, optGroup(OPT_TEXT_WITH_SPACE), remainder));
        patterns.addLinePattern(PATTERN_1991_LINE_2);
        patterns.addLinePattern(PATTERN_1991_LINE_3);
        patterns.addLinePattern(PATTERN_1991_LINE_4);
        patterns.addLinePattern(PATTERN_1991_LINE_5);
        return patterns;
    }

    /**
     * @return the header patterns for sessions 1777 to 1851 of 1994.
     */
    public static SessionHeaderPatterns create1994Session1777Patterns() {
        SessionHeaderPatterns patterns = new SessionHeaderPatterns("Session", nonCapt("Key Words|Keywords"));
        String altDateGroup = join(DATE_MONTH_PART, WSPACE, CAPT_DATE_DAY_PART, optGroup(WSPACE, DATE_YEAR_PART));
        patterns.addLinePattern(compilePattern("Session", MULTI_SPACE, ID_GROUP, ZOM_WSPACE,
                optGroup(optGroup(DASH + ZOM_WSPACE), capt("restriction", TEXT_WITH_SPACE)), reluctant(ZOM_WSPACE),
                OPT_DAY_GROUP, altDateGroup));
        patterns.addLinePattern(PATTERN_1994_SESSION_1777_LINE_2);
        patterns.addLinePattern(PATTERN_1988_LINE_3);
        patterns.addLinePattern(PATTERN_1994_LINE_4);
        patterns.addLinePattern(PATTERN_1994_LINE_5);
        return patterns;
    }

    /**
     * @param sessionMarker the session marker ("Session" or "Tape")
     * @return the header patterns for sessions starting from 1995.
     */
    public static SessionHeaderPatterns create1995Patterns(String sessionMarker) {
        SessionHeaderPatterns patterns = new SessionHeaderPatterns(sessionMarker, "Keywords");
        patterns.addLinePattern(compilePattern(sessionMarker + MULTI_SPACE, ID_GROUP, ZOM_WSPACE,
                reluctant(optGroup(optGroup(DASH + ZOM_WSPACE), RESTRICTION_GROUP)),
                optGroup(reluctant(ZOM_WSPACE), OPT_DAY_GROUP, opt(DATE_GROUP))));
        patterns.addLinePattern(PATTERN_1994_LINE_2);
        patterns.addLinePattern(PATTERN_1988_LINE_3);
        patterns.addLinePattern(PATTERN_1994_LINE_4);
        patterns.addLinePattern(PATTERN_1994_LINE_5);
        patterns.addLinePattern(PATTERN_1995_LINE_6);
        return patterns;
    }

    /**
     * @param sessionMarker   the session marker ("Session" or "Tape")
     * @param keyTopicsMarker the key topics marker ("Key Topics" or "Keywords")
     * @return the header patterns for sessions starting from 1995.
     */
    public static SessionHeaderPatterns create2002Patterns(String sessionMarker, String keyTopicsMarker) {
        SessionHeaderPatterns patterns = new SessionHeaderPatterns(sessionMarker, keyTopicsMarker);
        patterns.addLinePattern(compilePattern(sessionMarker + MULTI_SPACE, ID_GROUP, ZOM_WSPACE,
                reluctant(optGroup(optGroup(DASH + ZOM_WSPACE), RESTRICTION_GROUP)),
                optGroup(reluctant(ZOM_WSPACE), OPT_DAY_GROUP, opt(DATE_GROUP))));
        patterns.addLinePattern(PATTERN_1994_LINE_2);
        patterns.addLinePattern(PATTERN_2002_LINE_3);
        patterns.addLinePattern(PATTERN_1994_LINE_4);
        patterns.addLinePattern(PATTERN_1994_LINE_5);
        patterns.addLinePattern(PATTERN_1995_LINE_6);
        return patterns;
    }

    /**
     * @return the header patterns for sessions starting from 2004.
     */
    public static SessionHeaderPatterns create2004Patterns() {
        SessionHeaderPatterns patterns = new SessionHeaderPatterns("Session", "Key Topics");
        patterns.addLinePattern(
                compilePattern("Session" + MULTI_SPACE, OPT_ID_GROUP + optGroup(OPT_TEXT_WITH_SPACE) + MULTI_TAB,
                        RESTRICTION_GROUP, opt(nonCapt(MULTI_TAB, OPT_DAY_GROUP, DATE_GROUP))));
        patterns.addLinePattern(PATTERN_2004_LINE_2);
        patterns.addLinePattern(PATTERN_2004_LINE_3);
        return patterns;
    }

    /**
     * @return the header patterns for sessions starting from 2005.
     */
    public static SessionHeaderPatterns create2005Patterns() {
        SessionHeaderPatterns patterns = new SessionHeaderPatterns("Session", "Key Topics");
        patterns.addLinePattern(
                compilePattern("Session" + MULTI_SPACE, OPT_ID_GROUP, optGroup(OPT_TEXT_WITH_SPACE) + MULTI_TAB,
                        RESTRICTION_GROUP, MULTI_TAB, OPT_DAY_GROUP, DATE_GROUP));
        patterns.addLinePattern(PATTERN_2005_LINE_2);
        patterns.addLinePattern(PATTERN_2004_LINE_3);
        return patterns;
    }

    private void addLinePattern(Pattern linePattern) {
        headerLinePatterns.add(linePattern);
    }

    /**
     * @return the pattern that marks that a session in not available.
     */
    public Pattern getSessionUnavailablePattern() {
        return sessionUnavailablePattern;
    }

    /**
     * @return the pattern that marks the start of a session.
     */
    public Pattern getSessionStartPattern() {
        return sessionStartPattern;
    }

    /**
     * @return the keyTopicsPattern.
     */
    @CheckForNull
    public Pattern getKeyTopicsPattern() {
        return keyTopicsPattern;
    }

    /**
     * @return the headerLinePatterns.
     */
    public List<Pattern> getHeaderLinePatterns() {
        return headerLinePatterns;
    }

    private static Pattern compilePattern(String... parts) {
        return Pattern.compile("^" + join(parts) + "$");
    }
}
