/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import net.soundinglight.bo.TextType;
import org.junit.Test;

import static net.soundinglight.AssertExt.assertEnumValues;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class FontTest {

    @Test
    public void testShouldReturnFontMatchingByName() {
        assertSame(Font.SL_SOULWORD, Font.byName("S.L. SoulWord"));
    }

    @Test
    public void testShouldReturnFontMatchingByAliasName() {
        assertSame(Font.TIMES, Font.byName("Times New Roman"));
    }

    @Test
    public void testShouldReturnOtherWhenNotAMatchingName() {
        assertSame(Font.OTHER, Font.byName("nonMatching"));
    }

    @Test
    public void testEnumValues() throws Exception {
        assertEnumValues(Font.class);
    }

    @Test
    public void testSoulwordShouldYieldSacredCapsTextType() throws Exception {
        assertEquals(TextType.SACRED_CAPS, Font.SL_SOULWORD.getRelatedTextType());
    }

    @Test
    public void testCaswordShouldYieldSacredCapsTextType() throws Exception {
        assertEquals(TextType.SACRED_CAPS, Font.SL_CASWORD.getRelatedTextType());
    }

    @Test
    public void testCaslowShouldYieldSacredTextType() throws Exception {
        assertEquals(TextType.SACRED, Font.SL_CASLOW.getRelatedTextType());
    }
    @Test
    public void testOtherFontShouldYieldLatinTextType() throws Exception {
        assertEquals(TextType.LATIN, Font.TIMES.getRelatedTextType());
    }
}
