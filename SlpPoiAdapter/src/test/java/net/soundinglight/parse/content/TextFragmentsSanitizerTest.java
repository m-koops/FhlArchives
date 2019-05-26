/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.content;

import net.soundinglight.bo.*;
import net.soundinglight.parse.strategy.ParserStrategy2005;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TextFragmentsSanitizerTest {
    private static final String MIME_TYPE = "image/jpeg";
    private static final byte[] IMAGE_CONTENT = "test content".getBytes();
    private static final float WIDTH = 8.3f;
    private static final float HEIGHT = 13.1f;
    private static final Image SAMPLE = new Image(MIME_TYPE, IMAGE_CONTENT, WIDTH, HEIGHT);
    private static final TextFragmentsSanitizer SANITIZER = new TextFragmentsSanitizer(new ParserStrategy2005());

    @Test
    public void testShouldSanatizeEmptyFragmentList() {
        assertSanitize(Collections.<TextFragment>emptyList(), Collections.<TextFragment>emptyList());
    }

    @Test
    public void testSanatizeShouldProperlyHandlePictures() {
        assertSanitize(Arrays.<SlpParagraphElement>asList(createNormalFragment("test1 test2"), SAMPLE,
                createSacredFragment("test1 "), createNormalFragment("test2")),
                Arrays.asList(createNormalFragment("test1 "), createNormalFragment("test2"), SAMPLE,
                        createSacredFragment("test1 "), createNormalFragment("test2")));

    }

    @Test
    public void testShouldSanitizeSingleTextFrame() {
        List<TextFragment> fragments = Collections.singletonList(createBoldFragment("test"));
        assertSanitize(fragments, fragments);
    }

    @Test
    public void testShouldJoinTwoSimilarFragments() {
        assertJoin(Collections.singletonList(createNormalFragment("test1 test2")),
                Arrays.asList(createNormalFragment("test1 "), createNormalFragment("test2")));
    }

    @Test
    public void testShouldNotJoinFragmentsThatDifferByStyle() {
        assertJoin(Arrays.asList(createNormalFragment("test1 "), createBoldFragment("test2")),
                Arrays.asList(createNormalFragment("test1 "), createBoldFragment("test2")));
    }

    @Test
    public void testShouldNotJoinFragmentsThatDifferByType() {
        assertJoin(Arrays.asList(createNormalFragment("test1 "), createSacredFragment("test2")),
                Arrays.asList(createNormalFragment("test1 "), createSacredFragment("test2")));
    }

    @Test
    public void testShouldJoinLeadingWhitespaceFragmentsEvenWhenItDiffersByStyle() {
        assertJoin(Collections.singletonList(createBoldFragment("\ttest1")),
                Arrays.asList(createNormalFragment("\t"), createBoldFragment("test1")));
    }

    @Test
    public void testShouldJoinTrailingWhitespaceFragmentsEvenWhenItDiffersByStyle() {
        assertJoin(Collections.singletonList(createBoldFragment("test1\t")),
                Arrays.asList(createBoldFragment("test1"), createNormalFragment("\t")));
    }

    @Test
    public void testShouldJoinWhitespaceOnlyFragmentsEvenWhenTheyDifferByStyle() {
        assertJoin(Arrays.asList(createNormalFragment("test1\t "), createBoldFragment("test2")),
                Arrays.asList(createNormalFragment("test1"), createBoldFragment("\t "), createBoldFragment("test2")));
    }

    @Test
    public void testShouldJoinWhitespaceOnlyFragmentsEvenWhenTheyDifferByType() {
        assertJoin(Arrays.asList(createNormalFragment("test1\t "), createSacredFragment("test2")),
                Arrays.asList(createNormalFragment("test1"), createSacredFragment("\t "),
                        createSacredFragment("test2")));
    }

    @Test
    public void testShouldTrimLeadingWhitespacesFromFirstTextFragment() {
        assertSanitize(Arrays.asList(createNormalFragment("test1 "), createBoldFragment("test2")),
                Arrays.asList(createNormalFragment("\t test1 "), createBoldFragment("test2")));
    }

    @Test
    public void testShouldTrimTrailingWhitespacesFromLastTextFragment() {
        assertSanitize(Arrays.asList(createNormalFragment("test1 "), createBoldFragment("test2")),
                Arrays.asList(createNormalFragment("test1 "), createBoldFragment("test2 \t")));
    }

    @Test
    public void testShouldProperlyTrimAWhitespaceOnlyTextFragment() {
        assertSanitize(Collections.<TextFragment>emptyList(), Collections.singletonList(createNormalFragment(" \t")));
    }

    @Test
    public void testShouldStripWhitespaceFromFront() {
        assertStripFromSacred(
                Arrays.asList(createNormalFragment("red"), createNormalFragment(" "), createSacredFragment("blue")),
                Arrays.asList(createNormalFragment("red"), createSacredFragment(" blue")));
    }

    @Test
    public void testShouldStripWhitespaceFromBack() {
        assertStripFromSacred(
                Arrays.asList(createSacredFragment("red"), createNormalFragment(" "), createNormalFragment("blue")),
                Arrays.asList(createSacredFragment("red "), createNormalFragment("blue")));
    }

    @Test
    public void testShouldConsolidateSacredAtFront() {
        assertConsolidateSacred(Arrays.asList(createNormalFragment("red "), createSacredFragment("bluegreen")),
                Arrays.asList(createNormalFragment("red blue"), createSacredFragment("green")));
    }

    @Test
    public void testShouldConsolidateSacredAtBack() {
        assertConsolidateSacred(Arrays.asList(createSacredFragment("redblue"), createNormalFragment(" green")),
                Arrays.asList(createSacredFragment("red"), createNormalFragment("blue green")));
    }

    @Test
    public void testShouldNotConsolidatePunctuationAtFront() {
        assertConsolidateSacred(Arrays.asList(createNormalFragment("red ,"), createSacredFragment("blue")),
                Arrays.asList(createNormalFragment("red ,"), createSacredFragment("blue")));
    }

    @Test
    public void testShouldNotConsolidatePunctuationAtBack() {
        assertConsolidateSacred(Arrays.asList(createSacredFragment("red"), createNormalFragment(". blue")),
                Arrays.asList(createSacredFragment("red"), createNormalFragment(". blue")));
    }

    @Test
    public void testShouldNotConsolidateWhitespaceAtFront() {
        assertConsolidateSacred(Arrays.asList(createNormalFragment("red "), createSacredFragment("blue")),
                Arrays.asList(createNormalFragment("red "), createSacredFragment("blue")));
    }

    @Test
    public void testShouldNotConsolidateWhitespaceAtBack() {
        assertConsolidateSacred(Arrays.asList(createSacredFragment("red"), createNormalFragment("\\t blue")),
                Arrays.asList(createSacredFragment("red"), createNormalFragment("\\t blue")));
    }

    @Test
    public void testShouldConsolidateSacredOverMultipleFragments() {
        assertConsolidateSacred(
                Arrays.asList(createNormalFragment("red "), createSacredFragment("bluegreenyellowpurplepink"),
                        createNormalFragment(" violet")),
                Arrays.asList(createNormalFragment("red blue"), createNormalFragment("green"),
                        createSacredFragment("yellow"), createNormalFragment("purple"),
                        createNormalFragment("pink violet")));
    }

    @Test
    public void testShouldJoinFragmentsAfterConsolidatingSacredTogether() {
        assertSanitize(Collections.singletonList(createSacredFragment("redbluegreen yellowpurplepink")),
                Arrays.asList(createNormalFragment("red"), createSacredFragment("blue"),
                        createNormalFragment("green yellow"), createSacredFragment("purple"),
                        createNormalFragment("pink")));
    }

    private TextFragment createNormalFragment(String text) {
        return new TextFragment(TextType.LATIN, TextStyle.NORMAL, text);
    }

    private TextFragment createBoldFragment(String text) {
        return new TextFragment(TextType.LATIN, TextStyle.BOLD, text);
    }

    private TextFragment createSacredFragment(String text) {
        return new TextFragment(TextType.SACRED, TextStyle.NORMAL, text);
    }

    private void assertSanitize(List<? extends SlpParagraphElement> expected,
                                List<? extends SlpParagraphElement> elements) {
        List<? extends SlpParagraphElement> actual = SANITIZER.sanatize(elements);
        SlpParagraphElementTestUtil.assertElementsEquals(expected, actual);
    }

    private void assertJoin(List<TextFragment> expected, List<TextFragment> fragments) {
        List<? extends SlpParagraphElement> actual = SANITIZER.join(fragments);
        SlpParagraphElementTestUtil.assertElementsEquals(expected, actual);
    }

    private void assertConsolidateSacred(List<TextFragment> expected, List<TextFragment> fragments) {
        List<? extends SlpParagraphElement> actual = SANITIZER.consolidateToSacred(fragments);
        SlpParagraphElementTestUtil.assertElementsEquals(expected, actual);
    }

    private void assertStripFromSacred(List<TextFragment> expected, List<TextFragment> fragments) {
        List<? extends SlpParagraphElement> actual = SANITIZER.stripFromSacred(fragments);
        SlpParagraphElementTestUtil.assertElementsEquals(expected, actual);
    }
}
