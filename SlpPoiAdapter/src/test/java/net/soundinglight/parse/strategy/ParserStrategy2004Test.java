/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import net.soundinglight.bo.MomentOfDay;

import org.junit.Test;

public class ParserStrategy2004Test extends ParserStrategyTestBase {
	public ParserStrategy2004Test() {
		super(new ParserStrategy2004());
	}

	@Test
	public void testParseHeaderLine1() {
		assert1stLineMatches("Session 2817\tSemi-Restricted\tSaturday 24th July 2004", "2817",
				"Semi-Restricted", "Saturday", 24, "July", "2004");
		assert1stLineMatches("Session 2818\tUnrestricted \tSunday 25th July 2004", "2818",
				"Unrestricted ", "Sunday", 25, "July", "2004");
		assert1stLineMatches("Session 2847\tSemi-Restricted DT\tFriday 6th August 2004", "2847",
				"Semi-Restricted DT", "Friday", 6, "August", "2004");
		assert1stLineMatches("Session 2863\tSemi-Restricted DT    \tThursday 12th August 2004",
				"2863", "Semi-Restricted DT    ", "Thursday", 12, "August", "2004");
		assert1stLineMatches("Session 2883\t\tSemi-Restricted DT\t\tFriday 25th June 2004", "2883",
				"Semi-Restricted DT", "Friday", 25, "June", "2004");
		assert1stLineMatches("Session 2884\t\tUnrestricted \t\tSunday 27th June 2004", "2884",
				"Unrestricted ", "Sunday", 27, "June", "2004");
		assert1stLineMatches("Session 2887\tUnrestricted\tWednesday 24th November 2004", "2887",
				"Unrestricted", "Wednesday", 24, "November", "2004");
		assert1stLineMatches("Session 3745\tSemi-Restricted DT\tSunday 29th August 2010", "3745",
				"Semi-Restricted DT", "Sunday", 29, "August", "2010");
		assert1stLineMatches("Session 3751\tUnrestricted\tTuesday 31st August 2010", "3751",
				"Unrestricted", "Tuesday", 31, "August", "2010");
	}

	@Test
	public void testParseHeaderLine2() {
		assert2ndLineMatches("(Talk Unrestricted)\tEvening Session", "(Talk Unrestricted)",
				MomentOfDay.EVENING, null);
		assert2ndLineMatches("1st Morning Session", null, MomentOfDay.MORNING1, null);
		assert2ndLineMatches("(Talk Unrestricted - 1st 45 mins.)\tEvening Session",
				"(Talk Unrestricted - 1st 45 mins.)", MomentOfDay.EVENING, null);
		assert2ndLineMatches("(Talk Unrestricted, tracks 1-10)\t1st Morning Session",
				"(Talk Unrestricted, tracks 1-10)", MomentOfDay.MORNING1, null);
		assert2ndLineMatches("Morning Session", null, MomentOfDay.MORNING, null);
		assert2ndLineMatches("Afternoon Session", null, MomentOfDay.AFTERNOON, null);
	}

	@Test
	public void testParseHeaderLine3() {
		assert3rdLineMatches("", null, null, null);
		assert3rdLineMatches("Time:    25 mins.", 25, null, null);
		assert3rdLineMatches("Time:\t62 mins.", 62, null, null);
		assert3rdLineMatches("Time:\t87 mins.\tCD I\t71 mins.", 87, null, "CD I\t71 mins.");
	}
}
