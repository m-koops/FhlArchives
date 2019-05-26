/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import net.soundinglight.bo.MomentOfDay;

import org.junit.Test;

public class ParserStrategy1994Session1777To1851Test extends ParserStrategyTestBase {
	public ParserStrategy1994Session1777To1851Test() {
		super(new ParserStrategy1994Sessions1777To1851());
	}

	@Test
	public void testParseHeaderLine1() {
		assert1stLineMatches("Session 1777\t - Unrestricted\tSat. July 16 1994", "1777",
				"Unrestricted", "Sat.", 16, "July", "1994");
		assert1stLineMatches("Session 1778 - Semi-restricted\tSun. July 17", "1778",
				"Semi-restricted", "Sun.", 17, "July", null);
		assert1stLineMatches("Session 1799 -  Unrestricted\tSun. July 24 1994", "1799",
				"Unrestricted", "Sun.", 24, "July", "1994");
		assert1stLineMatches("Session 1806  -  Restricted\tWed. July 27 1994", "1806",
				"Restricted", "Wed.", 27, "July", "1994");
		assert1stLineMatches("Session 1807  -  Semi-restricted\tWed. July 27 1994", "1807",
				"Semi-restricted", "Wed.", 27, "July", "1994");
		assert1stLineMatches("Session 1829   -Restricted\tFri.  Aug. 5 1994", "1829", "Restricted",
				"Fri.", 5, "Aug.", "1994");
		assert1stLineMatches("Session 1830  - Restricted\tFri.  Aug. 5 1994", "1830", "Restricted",
				"Fri.", 5, "Aug.", "1994");
		assert1stLineMatches("Session 1845 \t- Semi-restricted\tThurs. Aug. 11 1994", "1845",
				"Semi-restricted", "Thurs.", 11, "Aug.", "1994");
	}

	@Test
	public void testParseHeaderLine2() {
		assert2ndLineMatches("Side A 44 min.\t\tEvening Session", 44, null, MomentOfDay.EVENING);
		assert2ndLineMatches("Side A 44 min.\t1st Morning Session", 44, null, MomentOfDay.MORNING1);
		assert2ndLineMatches("Side A 43 min.\t2nd Morning Session", 43, null, MomentOfDay.MORNING2);
		assert2ndLineMatches("Side A - 31min.\t2nd Morning Session", 31, null, MomentOfDay.MORNING2);
	}

	@Test
	public void testParseHeaderLine3() {
		assert3rdLineMatches("Side B  -\t31 mins.", 31, null, null);
		assert3rdLineMatches("Side B -\t0 mins.", 0, null, null);
	}
}
