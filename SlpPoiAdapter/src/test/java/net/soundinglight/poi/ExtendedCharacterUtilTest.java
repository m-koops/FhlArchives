/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi;

import static net.soundinglight.AssertExt.assertPrivateCtor;

import static org.junit.Assert.assertEquals;

import net.soundinglight.UnicodeConstants;

import org.junit.Test;

public class ExtendedCharacterUtilTest {
	private static final char SQR_ROOT = UnicodeConstants.SQR_ROOT;

	@Test
	public void testShouldShowUnicodeValuesForEachCharInText() throws Exception {
		assertEquals("test " + SQR_ROOT
				+ " [t(\\u0074), e(\\u0065), s(\\u0073), t(\\u0074),  (\\u0020), " + SQR_ROOT
				+ "(\\u221A)]", ExtendedCharacterUtil.showUnicodeValues("test " + SQR_ROOT));
	}

	@Test
	public void testShouldEncodeExtendedCharacters() throws Exception {
		String text =
				"test \t \u000C \u007F \u00A5 \u00A9 \u00AD \u00BF \u00C1 \u00C4 \u00E5 "
						+ SQR_ROOT;
		assertEquals("test \t {\\u000C} {\\u007F} {\\u00A5} \u00A9 {\\u00AD} {\\u00BF} "
				+ "\u00C1 \u00C4 {\\u00E5} \u221A", ExtendedCharacterUtil.encodeUnsupportedChars(
				text, CharacterReplacementMap.OTHER_FONT_REPLACEMENTS));
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(ExtendedCharacterUtil.class);
	}

}
