/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import net.soundinglight.bo.MomentOfDay;

import org.junit.Test;

public class ParserStrategy1988Test extends ParserStrategyTestBase {

	public ParserStrategy1988Test() {
		super(new ParserStrategy1988());
	}

	@Test
	public void testParseHeaderLine1() {
		assert1stLineMatches("Tape 717\t\t-\tUnrestricted\t\t\t17th August, 1988", "717",
				"Unrestricted", 17, "August", "1988");
		assert1stLineMatches("Tape 729\t\t-\tSemi-Restricted\t\t\t17th August, 1988", "729",
				"Semi-Restricted", 17, "August", "1988");
		assert1stLineMatches("Tape 731\t\t\t\t\t\t\t\t\t\t\t\t\t19th August, 1988", "731", null,
				19, "August", "1988");
	}

	@Test
	public void testParseHeaderLine2() {
		assert2ndLineMatches("Side A  -\t30 mins.\t\t\t\t\t\t\t\t1st Morning Session", 30, null,
				MomentOfDay.MORNING1);
		assert2ndLineMatches("Side A  -\t31 mins.\t\t\t\t\t\t\t(Talk Unrestricted)", 31,
				"(Talk Unrestricted)", null);
		assert2ndLineMatches("Side A  -\t29 mins.", 29, null, null);
		assert2ndLineMatches("Side A  -\t30 mins.\t\t\tSide A\t-\tRestricted", 30,
				"Side A\t-\tRestricted", null);
		assert2ndLineMatches("Side A  -\t31 mins.\t\t\t\t", 31, null, null);
		assert2ndLineMatches("Side A  -\t31 mins.\t\t\t\t(Talk Unrestricted)\t\t\t", 31,
				"(Talk Unrestricted)", null);
		assert2ndLineMatches("Side A  -\t31 mins.\t\t\t\t(Talk Unrestricted)\t\t\tMorning Session",
				31, "(Talk Unrestricted)", MomentOfDay.MORNING);
	}

	@Test
	public void testParseHeaderLine3() {
		assert3rdLineMatches("Side B  -\t31 mins.", 31, null, null);
		assert3rdLineMatches(
				"Side B  -\t31 mins.\t\t\t\t\t(Incomplete - check)\t(Questions hard to hear)", 31,
				null, "(Incomplete - check)\t(Questions hard to hear)");
		assert3rdLineMatches("Side B  -\t30 mins.\t\t\t\t\t\t\t\t\t\tT/L incomplete", 30, null,
				"T/L incomplete");
		assert3rdLineMatches(
				"Side B  -\t31 mins.\t\t\tSide B\t-\tSemi-Restricted DT\t\t\t\t\tT/L incomplete",
				31, "Side B\t-\tSemi-Restricted DT", "T/L incomplete");
		assert3rdLineMatches(
				"Side B  -\t31 mins.\t\t\t\t\t\t\t\t\tbackground hum and slight distortion\t\t\t\t\t\t??",
				31, null, "background hum and slight distortion");
		assert3rdLineMatches(
				"Side B  -\t44 mins.\t\t(Original recording extremely low volume. Remastered resulting "
						+ "in much hiss (turn \t\t\t\t\t\t\t\t\t\tdown the “Treble” on your machine) "
						+ "but more reasonable volume.)",
				44,
				null,
				"(Original recording extremely low volume. Remastered resulting in much hiss (turn "
						+ "\t\t\t\t\t\t\t\t\t\tdown the “Treble” on your machine) but more reasonable "
						+ "volume.)");
	}

}
