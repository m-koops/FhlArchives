/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import javax.annotation.CheckForNull;
import java.util.Collections;
import java.util.List;

import static net.soundinglight.AssertExt.assertBooleanEquals;
import static org.junit.Assert.assertEquals;

public final class SlpParagraphTestUtil {
    public static SlpParagraph createParagraph(int id, String text) {
        return createParagraph(id, Collections.singletonList(new TextFragment(text)));
    }

    public static SlpParagraph createParagraph(int id, List<? extends SlpParagraphElement> fragments) {
        return new SlpParagraph(id, fragments, Alignment.LEFT, false, null, null);
    }

    public static void assertSlpParagraphsEquals(List<SlpParagraph> expected, List<SlpParagraph> actual) {
        assertEquals("fragment count mismatch", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertSlpParagraphEquals("paragraph " + i, expected.get(i), actual.get(i));
        }
    }

    public static void assertSlpParagraphsEquals(@CheckForNull String message, List<SlpParagraph> expected,
                                                 List<SlpParagraph> actual) {
        String prefix = message == null ? "" : message + ", ";
        assertEquals(prefix + "paragraph count mismatch", expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertSlpParagraphEquals(prefix + "paragraph " + i, expected.get(i), actual.get(i));
        }
    }

    public static void assertSlpParagraphEquals(SlpParagraph expected, SlpParagraph actual) {
        assertSlpParagraphEquals(null, expected, actual);
    }

    public static void assertSlpParagraphEquals(@CheckForNull String message, SlpParagraph expected,
                                                SlpParagraph actual) {
        assertSlpParagraph(message, actual, expected.getId(), expected.getAlignment(), expected.getElements(),
                expected.hasIndent(), expected.getParagraphLabel(), expected.getBulletLabel());
    }

    public static void assertSlpParagraph(SlpParagraph paragraph, int id, Alignment alignment, boolean indent, List<?
            extends SlpParagraphElement> elements, @CheckForNull String paragraphLabel,
                                          @CheckForNull String bulletLabel) {
        assertSlpParagraph(null, paragraph, id, alignment, elements, indent, paragraphLabel, bulletLabel);
    }

    private static void assertSlpParagraph(@CheckForNull String message, SlpParagraph paragraph, int id,
                                           Alignment alignment, List<? extends SlpParagraphElement> elements,
                                           boolean indent, @CheckForNull String paragraphLabel,
                                           @CheckForNull String bulletLabel) {
        String prefix = message == null ? "" : message + ", ";
        assertEquals(prefix + "id mismatch", id, paragraph.getId());
        SlpParagraphElementTestUtil.assertElementsEquals(prefix, elements, paragraph.getElements());
        assertEquals(prefix + "alignment mismatch", alignment, paragraph.getAlignment());
        assertBooleanEquals(prefix + "indent mismatch", indent, paragraph.hasIndent());
        assertEquals(prefix + "paragraph label mismatch", paragraphLabel, paragraph.getParagraphLabel());
        assertEquals(prefix + "bullet label mismatch", bulletLabel, paragraph.getBulletLabel());
    }

    private SlpParagraphTestUtil() {
        // prevent instantiation
    }
}
