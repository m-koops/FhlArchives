/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.header;

import net.soundinglight.UnicodeConstants;
import net.soundinglight.bo.MomentOfDay;
import net.soundinglight.bo.Recommendation;
import net.soundinglight.bo.SessionDetails;
import net.soundinglight.parse.ParseException;
import net.soundinglight.util.StringUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.soundinglight.AssertExt.assertBooleanEquals;
import static org.junit.Assert.*;

public class SessionHeaderDetailsCollectorTest {
	private static final String KEY = "key";
	private static final String VALUE_1 = "value1";
	private static final String VALUE_2 = "value2";

	private SessionHeaderDetailsCollector collector;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setup() {
		collector = new SessionHeaderDetailsCollector(1993);
	}

	@Test
	public void testShouldYieldSingleSetValue() throws Exception {
		collector.addValue(KEY, VALUE_1);
		assertEquals(VALUE_1, collector.getSingleValue(KEY));
	}

	@Test
	public void testShouldYieldAllSetValues() throws Exception {
		collector.addValue(KEY, VALUE_1);
		collector.addValue(KEY, VALUE_2);
		List<String> values = collector.getValues(KEY);
		assertEquals(Arrays.asList(VALUE_1, VALUE_2), values);
	}

	@Test
	public void testShouldThrowWhenNotSingleValued() throws Exception {
		collector.addValue(KEY, VALUE_1);
		collector.addValue(KEY, VALUE_2);

		thrown.expect(ParseException.class);
		thrown.expectMessage("only a single value expected");
		collector.getSingleValue(KEY);
	}

	@Test
	public void testShouldYieldNullForUnknownDetail() throws Exception {
		assertNull(collector.getSingleValue(KEY));
		assertNull(collector.getValues(KEY));
	}

	@Test
	public void testShouldCollectDetailsFromMatcher() throws Exception {
		Pattern pattern = Pattern.compile("^.+(?<" + KEY + ">value\\d).+$");
		Matcher matcher = pattern.matcher("a sample containing " + VALUE_1 + " and some more");
		assertTrue(matcher.matches());

		collector.collectDetailsFromMatcher(matcher);
		assertEquals(VALUE_1, collector.getSingleValue(KEY));
	}

	@Test
	public void testShouldYieldProperValueForValidMonth() throws Exception {
		assertEquals(3, SessionHeaderDetailsCollector.determine1BasedMonthValue("March"));
	}

	@Test
	public void testShouldYieldDateBasedOnAttributes() throws ParseException {
		collector.addValue("date", "13");
		collector.addValue("month", "Feb.");
		collector.addValue("year", "1984");
		assertEquals("02/13/1984", collector.extractNormalizedSessionDate());
	}

	@Test
	public void testShouldYieldDateBasedOnAttributesAndTapelistYear() throws ParseException {
		collector.addValue("date", "13");
		collector.addValue("month", "Feb.");
		assertEquals("02/13/1993", collector.extractNormalizedSessionDate());
	}

	@Test
	public void testShouldThrowForInvalidMonth() throws Exception {
		thrown.expect(ParseException.class);
		thrown.expectMessage("'NotValid' cannot be parsed as a name or abbreviation of a month");
		SessionHeaderDetailsCollector.determine1BasedMonthValue("NotValid");
	}

	@Test
	public void testShouldYieldProperValueForValidAbbreviation() throws Exception {
		assertEquals(9, SessionHeaderDetailsCollector.determine1BasedMonthValue("Sept."));
	}

	@Test
	public void testShouldThrowForInvalidAbbreviation() throws Exception {
		thrown.expect(ParseException.class);
		thrown.expectMessage("'NotValid.' cannot be parsed as a name or abbreviation of a month");
		SessionHeaderDetailsCollector.determine1BasedMonthValue("NotValid.");
	}

	@Test
	public void testShouldThrowOnSessionWithoutId() throws Exception {
		thrown.expect(ParseException.class);
		thrown.expectMessage("failed to determine session id");

		new SessionHeaderDetailsCollector(1982).createSessionDetails();
	}

	@Test
	public void testGettersOfAvailailableSession() throws ParseException {
		List<String> keyTopics = Arrays.asList("Topic 1", "Topic 2", "Topic 3");
		SessionHeaderDetailsCollector collector = new SessionHeaderDetailsCollector(1982);
		collector.addValue("id", "1");
		collector.addValue("available", "true");
		collector.addValue("restriction", "Unrestricted");
		collector.addValue("subRestriction", "sub restriction");
		collector.addValue("date", "22");
		collector.addValue("month", "April");
		collector.addValue("year", "1982");
		collector.addValue("momentOfDay", "1st Morning Session");
		collector.addValue("location", "Amsterdam");
		collector.addValue("title", "Session title");
		collector.addValue("subTitle", "sub title");
		collector.addValue("duration", "53");
		collector.addValue("recommendation", "" + UnicodeConstants.SQR_ROOT);
		collector.addValue("keyTopics", StringUtil.join(keyTopics, "; "));
		collector.addValue("additionalRemarks", "some additional remark");
		collector.addValue("pal", "123");
		collector.addValue("pal", "456");
		collector.addValue("palRestriction", "Restricted");
		collector.addValue("palRestriction", "Semi-Restricted");
		collector.addValue("ntsc", "789");

		SessionDetails details = collector.createSessionDetails();

		assertEquals("1", details.getId());
		assertBooleanEquals(true, details.isAvailable());
		assertEquals("Session title", details.getTitle());
		assertEquals("sub title", details.getSubTitle());
		assertEquals("04/22/1982", details.getDate());
		assertEquals(Recommendation.LOW, details.getRecommendation());
		assertEquals(53, details.getDuration());
		assertEquals("Unrestricted", details.getRestriction());
		assertEquals(Collections.singletonList("sub restriction"), details.getSubRestrictions());
		assertEquals(MomentOfDay.MORNING1, details.getMomentOfDay());
		assertEquals(keyTopics, details.getKeyTopics());
	}
}
