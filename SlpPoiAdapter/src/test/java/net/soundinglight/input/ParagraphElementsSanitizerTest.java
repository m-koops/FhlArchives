/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.input;

import net.soundinglight.poi.bo.CharacterRun;
import net.soundinglight.poi.bo.Font;
import net.soundinglight.poi.bo.ParagraphElement;
import net.soundinglight.poi.bo.ParagraphElementTestUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParagraphElementsSanitizerTest {
    private static final ParagraphElementsSanitizer SANATIZER = new ParagraphElementsSanitizer();

    @Test
    public void testShouldSanatizeEmptyRunList() throws Exception {
        assertSanitize(Collections.<ParagraphElement>emptyList(), Collections.<ParagraphElement>emptyList());
    }

    @Test
    public void testShouldSanitizeSingleRun() throws Exception {
        List<ParagraphElement> elements = Collections.singletonList(createBoldRun("test"));
        assertSanitize(elements, elements);
    }

    @Test
    public void testShouldJoinTwoSimilarRuns() throws Exception {
        assertSanitize(Collections.singletonList(createNormalRun("test1 test2")),
                Arrays.asList(createNormalRun("test1 "), createNormalRun("test2")));
    }

    @Test
    public void testShouldNotJoinRunThatDifferByStyle() throws Exception {
        assertSanitize(Arrays.asList(createNormalRun("test1 "), createBoldRun("test2")),
                Arrays.asList(createNormalRun("test1 "), createBoldRun("test2")));
    }

    @Test
    public void testShouldNotJoinRunsThatDifferByFont() throws Exception {
        assertSanitize(Arrays.asList(createNormalRun("test1 "), createSoulwordRun("test2")),
                Arrays.asList(createNormalRun("test1 "), createSoulwordRun("test2")));
    }

    @Test
    public void testShouldJoinLeadingWhitespaceRunsEvenWhenItDiffersByStyle() throws Exception {
        assertSanitize(Collections.singletonList(createNormalRun("\ttest1")),
                Arrays.asList(createBoldRun("\t"), createNormalRun("test1")));
    }

    @Test
    public void testShouldJoinWhitespaceOnlyRunEvenWhenTheyDifferByStyle() throws Exception {
        assertSanitize(Arrays.asList(createNormalRun("test1\t "), createBoldRun("test2")),
                Arrays.asList(createNormalRun("test1"), createBoldRun("\t "), createBoldRun("test2")));
    }

    @Test
    public void testShouldJoinWhitespaceOnlyRunsEvenWhenTheyDifferByFont() throws Exception {
        assertSanitize(Arrays.asList(createNormalRun("test1\t "), createSoulwordRun("test2")),
                Arrays.asList(createNormalRun("test1"), createSoulwordRun("\t "), createSoulwordRun("test2")));
    }

    @Test
    public void testShouldTrimTrailingWhitespacesFromLastRun() throws Exception {
        assertSanitize(Arrays.asList(createNormalRun("test1 "), createBoldRun("test2")),
                Arrays.asList(createNormalRun("test1 "), createBoldRun("test2 \t")));
    }

    @Test
    public void testShouldProperlyTrimAWhitespaceOnlyRun() throws Exception {
        assertSanitize(Collections.<ParagraphElement>emptyList(), Collections.singletonList(createNormalRun(" \t")));
    }

    @Test
    public void testShouldSkipPicture() throws Exception {
        assertSanitize(Arrays.asList(createNormalRun("test1\t"), ParagraphElementTestUtil.createTestPictureElement(),
                createNormalRun(" test2")), Arrays.asList(createNormalRun("test1"), createBoldRun("\t"),
                ParagraphElementTestUtil.createTestPictureElement(), createBoldRun(" "), createNormalRun("test2")));
    }

    @Test
    public void testShouldNotTrimTrailingWhitespacesFromLastRunIfFollowByPicture() {
        assertSanitize(Arrays.asList(createNormalRun("test1 "), createBoldRun("test2 \t"),
                ParagraphElementTestUtil.createTestPictureElement()),
                Arrays.asList(createNormalRun("test1 "), createBoldRun("test2 \t"),
                        ParagraphElementTestUtil.createTestPictureElement()));

    }

    private ParagraphElement createNormalRun(String text) {
        return new CharacterRun(text, Font.TIMES, null, false, false);
    }

    private ParagraphElement createBoldRun(String text) {
        return new CharacterRun(text, Font.TIMES, null, true, false);
    }

    private ParagraphElement createSoulwordRun(String text) {
        return new CharacterRun(text, Font.SL_SOULWORD, null, false, false);
    }

    private void assertSanitize(List<? extends ParagraphElement> expected, List<? extends ParagraphElement> elements) {
        List<? extends ParagraphElement> actual = SANATIZER.sanitize(elements);
        ParagraphElementTestUtil.assertParagraphElementsEquals(expected, actual);
    }
}
