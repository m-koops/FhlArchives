/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import net.soundinglight.bo.MomentOfDay;

import org.junit.Test;

public class ParserStrategy1995Test extends ParserStrategyTestBase {

	public ParserStrategy1995Test() {
		super(new ParserStrategy1995("Tape"));
	}

	@Test
	public void testParseHeaderLine1() {
		assert1stLineMatches("Tape 1867\t\t-\tUnrestricted\t\tTuesday, 13th December, 1994",
				"1867", "Unrestricted", "Tuesday", 13, "December", "1994");
		assert1stLineMatches("Tape 2044\t\t-\tSemi-Restricted\t\tWednesday, 18th October, 1995",
				"2044", "Semi-Restricted", "Wednesday", 18, "October", "1995");
		assert1stLineMatches(
				"Tape 2041\t\t\t\t\t\t-\tSemi-Restricted DT\t\t\t\t\tTuesday, 17th October, 1995",
				"2041", "Semi-Restricted DT", "Tuesday", 17, "October", "1995");
		assert1stLineMatches("Tape 2688 (1)\t\tSemi-Restricted \t\tMonday 7th April 2003",
				"2688 (1)", "Semi-Restricted ", "Monday", 7, "April", "2003");
		assert1stLineMatches("Tape 2688 (2)", "2688 (2)", null, null, null, null, null);
	}

	@Test
	public void testParseHeaderLine2() {
		assert2ndLineMatches("Side A  -  44 mins.\t\t\t\t\t\t\t2nd Morning Session", 44, null,
				MomentOfDay.MORNING2, null);
		assert2ndLineMatches("Side A  -  44 mins.\t\t\t\t\t\tCottonwood, Arizona, U.S.A.", 44,
				null, null, "Cottonwood, Arizona, U.S.A.");
		assert2ndLineMatches(
				"Side A  -  45 mins.\t\t\t(Talk Unrestricted)\t\t\t1st Morning Session", 45,
				"(Talk Unrestricted)", MomentOfDay.MORNING1, null);
		assert2ndLineMatches("Side A\t45 mins.\t\t\t\t\t\t\t\tAlmere, Holland", 45, null, null,
				"Almere, Holland");
		assert2ndLineMatches("Side A  -  8 mins.", 8, null, null, null);
	}

	@Test
	public void testParseHeaderLine3() {
		assert3rdLineMatches("Side B -\t33 mins.", 33, null, null);
		assert3rdLineMatches("Side B -\t12 mins.\t\t\t\t\t\t(except 14 mins. instruction)", 12,
				null, "(except 14 mins. instruction)");
	}
}
