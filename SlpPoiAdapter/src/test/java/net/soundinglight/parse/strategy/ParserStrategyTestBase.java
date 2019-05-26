/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.CheckForNull;

import net.soundinglight.bo.MomentOfDay;

public abstract class ParserStrategyTestBase {
	private static final Pattern GROUP_NAMES_PATTERN =
			Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>");
	private final ParserStrategy strategy;

	protected ParserStrategyTestBase(ParserStrategy strategy) {
		this.strategy = strategy;
	}

	protected void assert1stLineMatches(String line, String sessionId, String restriction,
			int date, String month, String year) {
		assert1stLineMatches(line, sessionId, restriction, null, date, month, year);
	}

	protected void assert1stLineMatches(String line, String sessionId, String restriction,
			String day, int date, String month, String year) {
		assert1stLineMatches(line, sessionId, restriction, day, Integer.valueOf(date), month, year);
	}

	protected void assert1stLineMatches(String line, String sessionId, String restriction,
			String day, Integer date, String month, String year) {
		assertLineMatches(1, line, safeIntegerToString(date), day, sessionId, month, restriction,
				year);
	}

	protected void assert2ndLineMatches(String line, int duration, String subRestriction,
			@CheckForNull MomentOfDay momentOfDay) {
		assert2ndLineMatches(line, Integer.valueOf(duration), subRestriction, momentOfDay, null);
	}

	protected void assert2ndLineMatches(String line, String subRestriction,
			@CheckForNull MomentOfDay momentOfDay, String location) {
		assert2ndLineMatches(line, null, subRestriction, momentOfDay, location);
	}

	protected void assert2ndLineMatches(String line, int duration, String subRestriction,
			@CheckForNull MomentOfDay momentOfDay, String location) {
		assert2ndLineMatches(line, Integer.valueOf(duration), subRestriction, momentOfDay, location);
	}

	protected void assert2ndLineMatches(String line, Integer duration, String subRestriction,
			@CheckForNull MomentOfDay momentOfDay, String location) {
		List<String> expected = new ArrayList<>();
		if (duration != null) {
			expected.add(duration.toString());
		}
		expected.addAll(Arrays.asList(location,
				momentOfDay == null ? null : momentOfDay.getDisplayName(), subRestriction));

		assertLineMatches(2, line, expected.toArray(new String[expected.size()]));
	}

	protected void assert3rdLineMatches(String line, int duration,
			@CheckForNull String additionalRemark) {
		assert3rdLineMatches(line, duration, null, additionalRemark);
	}

	protected void assert3rdLineMatches(String line, int duration, String subRestriction,
			@CheckForNull String additionalRemark) {
		assert3rdLineMatches(line, Integer.valueOf(duration), subRestriction, additionalRemark);
	}

	protected void assert3rdLineMatches(String line, @CheckForNull Integer duration,
			String subRestriction, @CheckForNull String additionalRemark) {
		assertLineMatches(3, line, additionalRemark, safeIntegerToString(duration), subRestriction);
	}

	@CheckForNull
	private String safeIntegerToString(@CheckForNull Integer value) {
		return value == null ? null : String.valueOf(value);
	}

	private void assertLineMatches(int patternNr, String line, String... expected) {
		Pattern pattern = strategy.getSessionHeaderPatterns().get(patternNr + 1);
		List<String> actual = assertMatchAndGetGroupValues(pattern, line);
		List<String> exp = Arrays.asList(expected);
		if (!exp.equals(actual)) {
			System.out.println("groups do not match expectations\n\tline: '" + line
					+ "'\n\tpattern: '" + pattern.pattern() + "'\n\texpected:\t" + exp
					+ "\n\tactual:\t\t" + actual);
		}
		assertEquals("line '" + line.replace("\t", "\\t") + "'", exp, actual);

	}

	private List<String> assertMatchAndGetGroupValues(Pattern pattern, String line) {
		Matcher matcher = pattern.matcher(line);
		if (!matcher.matches()) {
			String p = "'" + pattern.pattern() + "'";
			System.out.println("cannot parse header line\n\tline: '" + line + "'\n\tpattern: " + p);
			fail("line '" + line.replace("\t", "\\t") + "'" + " does not match " + p);
		}

		List<String> groupValues = new ArrayList<>(matcher.groupCount());
		for (String groupName : getGroupNames(pattern)) {
			groupValues.add(matcher.group(groupName));
		}

		return groupValues;
	}

	private List<String> getGroupNames(Pattern pattern) {
		List<String> groupNames = new ArrayList<>();
		Matcher groupnamesMatcher = GROUP_NAMES_PATTERN.matcher(pattern.pattern());
		while (groupnamesMatcher.find()) {
			groupNames.add(groupnamesMatcher.group(1));
		}

		Collections.sort(groupNames);
		return groupNames;
	}
}
