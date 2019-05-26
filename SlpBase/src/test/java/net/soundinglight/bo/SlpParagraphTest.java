/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SlpParagraphTest {
    private static final String SERIALIZED_RESOURCE = "../bo/serializedSlpParagraph.xml";
    private static final List<? extends SlpParagraphElement> ELEMENTS = Arrays.asList(TextFragmentTestUtil.NORMAL,
            ImageTest.SAMPLE, TextFragmentTestUtil.SACRED, TextFragmentTestUtil.ITALIC,
            TextFragmentTestUtil.BOLD_WHITESPACE);
    private static final SlpParagraph SAMPLE = new SlpParagraph(1, ELEMENTS, Alignment.CENTER, true, "paragraph label",
            "bullet label");

    @Test
    public void testGetters() {
        assertEquals(1, SAMPLE.getId());
        assertEquals(ELEMENTS, SAMPLE.getElements());
        assertEquals(Alignment.CENTER, SAMPLE.getAlignment());
        assertTrue(SAMPLE.hasIndent());
        assertEquals("paragraph label", SAMPLE.getParagraphLabel());
        assertEquals("bullet label", SAMPLE.getBulletLabel());
        assertEquals("latinsacreditalic\t", SAMPLE.asPlainText());
    }

    @Test
    public void testShouldYieldEmptyIsTrueOnNoLabelNoTextFragmentAndNoImage() {
        assertTrue(SlpParagraphTestUtil.createParagraph(1, Collections.<TextFragment>emptyList()).isEmpty());
    }

    @Test
    public void testShouldYieldEmptyIsTrueOnEmptyLabelsAndEmptyTextFragment() {
        assertTrue(SlpParagraphTestUtil.createParagraph(1, Collections.singletonList(TextFragmentTestUtil.EMPTY))
                .isEmpty());
    }

    @Test
    public void testShouldYieldEmptyIsFalseOnNonEmptyTextFragment() {
        assertFalse(SlpParagraphTestUtil.createParagraph(1, Collections.singletonList(TextFragmentTestUtil.NORMAL))
                .isEmpty());
    }

    @Test
    public void testShouldYieldEmptyIsFalseOnImage() {
        assertFalse(new SlpParagraph(1, Collections.singletonList(ImageTest.SAMPLE), Alignment.LEFT, false, null,
                null).isEmpty());
    }

    @Test
    public void testShouldYieldEmptyIsFalseOnNonEmptyParagraphLabel() {
        assertFalse(new SlpParagraph(1, Collections.<TextFragment>emptyList(), Alignment.LEFT, true, "paragraph label",
                null).isEmpty());
    }

    @Test
    public void testShouldYieldEmptyIsFalseOnNonEmptyBulletLabel() {
        assertFalse(new SlpParagraph(1, Collections.<TextFragment>emptyList(), Alignment.LEFT, true, null,
                "bullet label").isEmpty());
    }

    @Test
    public void testMarshalling() throws Exception {
        MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
    }

    @Test
    public void testUnmarshalling() throws Exception {
        SlpParagraphTestUtil.assertSlpParagraphEquals(SAMPLE,
                MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, SlpParagraph.class));
    }

    @Test
    public void testToStringShouldYieldDecentTextRepresentation() {
        assertEquals(
                "id=1; 'paragraph label'; true; [latin (latin/normal), image/jpeg; size= 8.3*13.1 (w*h in mm);" + " " + "content=dGVz" + "...ZW50, sacred (sacred/normal), italic (latin/italic), 	 " + "(latin" + "/bold)]",
                SAMPLE.toString());
    }
}
