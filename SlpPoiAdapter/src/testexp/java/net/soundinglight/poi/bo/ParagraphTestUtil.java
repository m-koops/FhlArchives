/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public final class ParagraphTestUtil {
    private static final CharacterRun RUN1 = ParagraphElementTestUtil.createSlSoulwordCharRun("run1");
    private static final CharacterRun RUN2 = ParagraphElementTestUtil.createZapfDingbatsCharRun("run2");
    private static final Picture PICTURE1 = ParagraphElementTestUtil.createTestPictureElement();
    public static final List<? extends ParagraphElement> ELEMENTS = Arrays.asList(RUN1, PICTURE1, RUN2);

    public static Paragraph createParagraph(int paragraphId) {
        return new Paragraph(paragraphId, 123, 456, RUN1, PICTURE1, RUN2);
    }

    public static Paragraph createParagraph(String text) {
        return new Paragraph(1, 123, 456, ParagraphElementTestUtil.createNormalCharRun(text));
    }

    public static void assertParagraphsEquals(List<Paragraph> expected, List<Paragraph> actual) {
        assertParagraphsEquals(null, expected, actual);
    }

    public static void assertParagraphEquals(@CheckForNull String message, Paragraph expected, Paragraph actual) {
        String prefix = message == null ? "" : message + ", ";

        assertEquals(prefix + "alignment mismatch", expected.getTextAlignment(), actual.getTextAlignment());
        assertEquals(prefix + "indentFromLeft mismatch", expected.getIndentFromLeft(), actual.getIndentFromLeft());
        assertEquals(prefix + "firstLineIndent mismatch", expected.getFirstLineIndent(), actual.getFirstLineIndent());
        ParagraphElementTestUtil.assertParagraphElementsEquals(prefix + "elements", expected.getElements(),
                actual.getElements());
        assertEquals(prefix + "text mismatch", expected.getText(), actual.getText());
    }

    public static void assertParagraphsEquals(@CheckForNull String message, List<Paragraph> expected,
											  List<Paragraph> actual) {
        String prefix = message == null ? "" : message + ", ";
        assertEquals(prefix, expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            ParagraphTestUtil.assertParagraphEquals(prefix + "paragraph " + i, expected.get(i), actual.get(i));
        }
    }

    public static void assertParagraphEquals(Paragraph expected, Paragraph actual) {
        assertParagraphEquals(null, expected, actual);
    }

    public static void assertParagraphIds(List<Paragraph> paragraphs, int... ids) {
        List<Integer> expected = new ArrayList<>(ids.length);
        for (int id : ids) {
            expected.add(Integer.valueOf(id));
        }
        List<Integer> actual = paragraphs.stream().map(p -> Integer.valueOf(p.getId())).collect(Collectors.toList());
        assertEquals(expected, actual);
    }

    private ParagraphTestUtil() {
        // prevent instantiation
    }
}
