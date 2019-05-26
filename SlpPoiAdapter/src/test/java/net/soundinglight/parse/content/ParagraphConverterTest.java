/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.content;

import net.soundinglight.bo.*;
import net.soundinglight.parse.strategy.ParserStrategy2005;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.poi.bo.ParagraphElementTestUtil;
import net.soundinglight.poi.bo.TextAlignment;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParagraphConverterTest {
    private static final ParserStrategy2005 STRATEGY = new ParserStrategy2005();

    @Test
    public void testShouldConvertEmptyParagraph() {
        Paragraph paragraph = new Paragraph(1, 284, 0);
        assertConvert(paragraph, 1, Alignment.JUSTIFY, false, null, null);
    }

    @Test
    public void testShouldConvertParagraphWithMultipleCharacterRuns() {
        Paragraph paragraph = new Paragraph(2, 284, 0, ParagraphElementTestUtil.createBoldCharRun("Test1"),
                ParagraphElementTestUtil.createItalicCharRun("Test2"));
        assertConvert(paragraph, 2, Alignment.JUSTIFY, false, null, null,
                new TextFragment(TextType.LATIN, TextStyle.BOLD, "Test1"),
                new TextFragment(TextType.LATIN, TextStyle.ITALIC, "Test2"));
    }

    @Test
    public void testShouldExtractNumericParagraphLabel() {
        Paragraph paragraph = new Paragraph(3, 284, 0, ParagraphElementTestUtil.createNormalCharRun("10\tTest"));
        assertConvert(paragraph, 3, Alignment.JUSTIFY, false, "10", null,
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "Test"));
    }

    @Test
    public void testShouldExtractDashParagraphLabel() {
        Paragraph paragraph = new Paragraph(4, 284, 0, ParagraphElementTestUtil.createNormalCharRun("-\tTest"));
        assertConvert(paragraph, 4, Alignment.JUSTIFY, false, "-", null,
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "Test"));
    }

    @Test
    public void testShouldNotExtractLabelsFromCenterParagraph() {
        Paragraph paragraph = new Paragraph(5, TextAlignment.CENTER, 284, 0,
                ParagraphElementTestUtil.createNormalCharRun("10\tTest"));
        assertConvert(paragraph, 5, Alignment.CENTER, false, null, null,
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "10\tTest"));
    }

    @Test
    public void testShouldNotExtractLabelsFromRightAlignedParagraph() {
        Paragraph paragraph = new Paragraph(6, TextAlignment.RIGHT, 284, 0,
                ParagraphElementTestUtil.createNormalCharRun("10\tTest"));
        assertConvert(paragraph, 6, Alignment.RIGHT, false, null, null,
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "10\tTest"));
    }

    @Test
    public void testShouldNotExtractLabelsFromTrackLengthParagraph() {
        Paragraph paragraph = new Paragraph(6, TextAlignment.LEFT, 284, 0,
                ParagraphElementTestUtil.createNormalCharRun("5 mins. (40')"));
        assertConvert(paragraph, 6, Alignment.LEFT, false, null, null,
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "5 mins. (40')"));
    }

    @Test
    public void testShouldExtractParagraphLabelFromParagraphWithMultiFragments() {
        Paragraph paragraph = new Paragraph(6, TextAlignment.LEFT, 284, 0,
                ParagraphElementTestUtil.createNormalCharRun("3\t\t"),
                ParagraphElementTestUtil.createSlSoulwordCharRun("Buddham Sharanam Gacchami"));
        assertConvert(paragraph, 6, Alignment.LEFT, false, "3", null,
                new TextFragment(TextType.SACRED_CAPS, TextStyle.NORMAL, "Buddham Sharanam Gacchami"));
    }

    @Test
    public void testShouldNotExtractLabelsFromParagraphWithFirstLineIndentGreaterThanParagraphIndent() {
        Paragraph paragraph = new Paragraph(7, 284, 290, ParagraphElementTestUtil.createNormalCharRun("10\tTest"));
        assertConvert(paragraph, 7, Alignment.JUSTIFY, false, null, null,
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "10\tTest"));
    }

    @Test
    public void testShouldExtractNumericBulletLabel() {
        Paragraph paragraph = new Paragraph(3, 284, 0, ParagraphElementTestUtil.createNormalCharRun("-\t10 Test"));
        assertConvert(paragraph, 3, Alignment.JUSTIFY, false, "-", "10",
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "Test"));
    }

    @Test
    public void testShouldExtractExperienceBulletLabel() {
        Paragraph paragraph = new Paragraph(3, 284, 0, ParagraphElementTestUtil.createNormalCharRun("E Test"));
        assertConvert(paragraph, 3, Alignment.JUSTIFY, false, null, "E",
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "Test"));
    }

    @Test
    public void testShouldExtractQuestionBulletLabel() {
        Paragraph paragraph = new Paragraph(3, 284, 0, ParagraphElementTestUtil.createNormalCharRun("Q Test"));
        assertConvert(paragraph, 3, Alignment.JUSTIFY, false, null, "Q",
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "Test"));
    }

    @Test
    public void testShouldExtractAnswerBulletLabel() {
        Paragraph paragraph = new Paragraph(3, 284, 0, ParagraphElementTestUtil.createNormalCharRun("A Test"));
        assertConvert(paragraph, 3, Alignment.JUSTIFY, false, null, "A",
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "Test"));
    }

    @Test
    public void testShouldMarkParagraphAsIndentedWithIndentExceedingTreshold() {
        Paragraph paragraph = new Paragraph(8, 600, 0, ParagraphElementTestUtil.createNormalCharRun("Test"));
        assertConvert(paragraph, 8, Alignment.JUSTIFY, true, null, null,
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "Test"));
    }

    @Test
    public void testShouldMarkParagraphAsIndentedWithFirstLineIndentExceedingTreshold() {
        Paragraph paragraph = new Paragraph(8, 0, 600, ParagraphElementTestUtil.createNormalCharRun("Test"));
        assertConvert(paragraph, 8, Alignment.JUSTIFY, true, null, null,
                new TextFragment(TextType.LATIN, TextStyle.NORMAL, "Test"));
    }

    private void assertConvert(Paragraph paragraph, int id, Alignment alignment, boolean indent,
                               String paragraphLabel, String bulletLabel, TextFragment... fragments) {
        List<SlpParagraph> slpParagraphs = new ParagraphConverter(STRATEGY).convert(
                Collections.singletonList(paragraph), ParagraphConverter.Mode.SESSION);
        assertEquals(1, slpParagraphs.size());
        SlpParagraphTestUtil.assertSlpParagraph(slpParagraphs.get(0), id, alignment, indent, Arrays.asList(fragments),
                paragraphLabel, bulletLabel);
    }
}
