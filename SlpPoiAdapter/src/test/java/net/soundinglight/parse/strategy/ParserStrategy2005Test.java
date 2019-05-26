/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import org.junit.Test;

public class ParserStrategy2005Test extends ParserStrategyTestBase {
	public ParserStrategy2005Test() {
		super(new ParserStrategy2005());
	}

	@Test
	public void testParseHeaderLine1() {
		assert1stLineMatches("Session 1\tUnrestricted\t22nd April 1982", "1", "Unrestricted", 22,
				"April", "1982");
		assert1stLineMatches("Session 5\tSemi-Restricted\t19th February 1982", "5",
				"Semi-Restricted", 19, "February", "1982");
		assert1stLineMatches("Session 15\tSemi-Restricted\t2nd June, 1982", "15",
				"Semi-Restricted", 2, "June", "1982");
	}

	@Test
	public void testParseHeaderLine2() {
		assert2ndLineMatches("Hamilton, N.Z.", null, null, "Hamilton, N.Z.");
		assert2ndLineMatches("(Talk Unrestricted: tracks 1-6, 11-17)\tChristchurch, N.Z.",
				"(Talk Unrestricted: tracks 1-6, 11-17)", null, "Christchurch, N.Z.");
	}

	@Test
	public void testParseHeaderLine3() {
		assert3rdLineMatches("Time:\t53 mins.", 53, null, null);
		assert3rdLineMatches("Time:\t47 mins.\t\t\tRefer also to Session 1403 for the Beatitudes",
				47, null, "Refer also to Session 1403 for the Beatitudes");
	}
}
