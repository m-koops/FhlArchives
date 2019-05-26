/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.soundinglight.jaxb.MarshalTestUtil;

public class ParagraphTest {
	private static final String SERIALIZED_PARAGRAPH_RESOURCE = "../poi/bo/serializedParagraph.xml";
	private static final Paragraph SAMPLE = ParagraphTestUtil.createParagraph(1);

	@Test
	public void testGetters() {
		assertEquals(TextAlignment.JUSTIFY, SAMPLE.getTextAlignment());
		assertEquals(123, SAMPLE.getIndentFromLeft());
		assertEquals(456, SAMPLE.getFirstLineIndent(), SAMPLE.getFirstLineIndent());
		assertEquals(ParagraphTestUtil.ELEMENTS, SAMPLE.getElements());
		assertEquals("run1run2", SAMPLE.getText());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_PARAGRAPH_RESOURCE, SAMPLE, CharacterRun.class, Picture.class);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		Paragraph instance = MarshalTestUtil.unmarshal(SERIALIZED_PARAGRAPH_RESOURCE, Paragraph.class);
		ParagraphTestUtil.assertParagraphEquals(SAMPLE, instance);
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("[run1, mime: image/jpeg; hor. scale fact: 0.123; "
				+ "vert. scale factor: 1.0; DxaGoalMm: 30; DyaGoalMm: 40; " + "base64Content: 'c2FtcGxlIG...', run2]",
				SAMPLE.toString());
	}
}
