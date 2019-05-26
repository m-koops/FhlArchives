/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.header;

import net.soundinglight.UnicodeConstants;
import net.soundinglight.bo.MomentOfDay;
import net.soundinglight.bo.SessionDetails;
import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.bo.SlpParagraphTestUtil;
import net.soundinglight.parse.ParseException;
import net.soundinglight.parse.strategy.ParserStrategy;
import net.soundinglight.parse.strategy.ParserStrategy1988;
import net.soundinglight.parse.strategy.ParserStrategy2004;
import net.soundinglight.parse.strategy.ParserStrategy2005;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static net.soundinglight.AssertExt.assertBooleanEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SessionHeaderParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testShouldParseAvailableSession1() throws ParseException {
        SessionDetails details = parseHeader(new ParserStrategy2005(), 1982, "Session 1\tUnrestricted\t22nd April 1982",
                "Amsterdam, NL", "Time:\t53 mins.", "\t\t\t " + createRecommendation(3), "", "",
                "Key Topics: Topic1; Topic 2. AA/BB");
        assertEquals("1", details.getId());
        assertBooleanEquals(true, details.isAvailable());
        assertEquals("Unrestricted", details.getRestriction());
        assertNull(details.getSubRestrictions());
        assertEquals("04/22/1982", details.getDate());
        assertEquals(MomentOfDay.UNKNOWN, details.getMomentOfDay());
        assertEquals("Amsterdam, NL", details.getLocation());
        assertEquals(53, details.getDuration());
    }

    @Test
    public void testShouldParseAvailableSession2817() throws ParseException {
        SessionDetails details = parseHeader(new ParserStrategy2004(), 2004,
                "Session 2817\tSemi-Restricted\tSaturday 24th July 2004", "(Talk Unrestricted)\tEvening Session",
                "\t\t\t\tSound slightly distorted\t\t(T/L incomplete)", "Time:\t52 mins.\t" + createRecommendation(2),
                "", "Key Topics: Topic1; Topic 2.");
        assertEquals("2817", details.getId());
        assertBooleanEquals(true, details.isAvailable());
        assertEquals("Semi-Restricted", details.getRestriction());
        assertEquals(Collections.singletonList("(Talk Unrestricted)"), details.getSubRestrictions());
        assertEquals("07/24/2004", details.getDate());
        assertEquals(MomentOfDay.EVENING, details.getMomentOfDay());
        assertEquals(Collections.singletonList("Sound slightly distorted\t\t(T/L incomplete)"),
                details.getAdditionalRemarks());
        assertNull(details.getLocation());
    }

    @Test
    public void testShouldParseAvailableSession727() throws ParseException {
        SessionDetails details = parseHeader(new ParserStrategy1988(), 1988,
                "Tape 727\t\t\t-\tUnrestricted\t\t\t17th August, 1988", "\tSide A -\t31 mins.\t\t\t1st Morning Session",
                "\tSide B -\t31 mins.\t\t\t(Incomplete - check)\t(Questions hard to hear)", "", "Keywords:");
        assertEquals("727", details.getId());
        assertBooleanEquals(true, details.isAvailable());
        assertEquals("Unrestricted", details.getRestriction());
        assertNull(details.getSubRestrictions());
        assertEquals("08/17/1988", details.getDate());
        assertEquals(MomentOfDay.MORNING1, details.getMomentOfDay());
        assertNull(details.getLocation());
        assertEquals(-1, details.getDuration());
        assertEquals(31, details.getDurationSideA());
        assertEquals(31, details.getDurationSideB());
    }

    @Test
    public void testShouldThrowOnNoHeaderLines() throws ParseException {
        thrown.expect(ParseException.class);
        thrown.expectMessage("no header lines found");

        parseHeader(new ParserStrategy2005(), 2005);
    }

    @Test
    public void testShouldThrowOnNonMatchingSecondLine() throws ParseException {
        parseHeader(new ParserStrategy2004(), 2004, "Session 1\tUnrestricted\t22nd April 1982",
                "This\tline\tis\tinvalid");
    }

    private SessionDetails parseHeader(ParserStrategy strategy, int tapelistYear, String... lines) throws ParseException {
        List<SlpParagraph> paragraphs = Arrays.stream(lines)
                .map(text -> SlpParagraphTestUtil.createParagraph(1, text))
                .collect(Collectors.toList());
        return new SessionHeaderParser(strategy, tapelistYear).parseSessionHeader(paragraphs);
    }

    private String createRecommendation(int recommendation) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < recommendation; i++) {
            builder.append(UnicodeConstants.SQR_ROOT);
        }
        return builder.toString();
    }
}
