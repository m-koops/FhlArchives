/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import static org.junit.Assert.assertEquals;

import org.apache.commons.codec.Charsets;
import org.junit.Test;

import net.soundinglight.jaxb.MarshalTestUtil;

public class PictureTest {
	private static final Picture SAMPLE = ParagraphElementTestUtil.createTestPictureElement();

	@Test
	public void testGetters() {
		assertEquals(ParagraphElementTestUtil.MIME_TYPE, SAMPLE.getMimeType());
		assertEquals(ParagraphElementTestUtil.SAMPLE_CONTENT, new String(SAMPLE.getBytes(), Charsets.UTF_8));
		assertEquals(ParagraphElementTestUtil.HOR_SCALING_FACT, SAMPLE.getHorizontalScalingFactor(), 0f);
		assertEquals(ParagraphElementTestUtil.VERT_SCALING_FACT, SAMPLE.getVerticalScalingFactor(), 0f);
		assertEquals(ParagraphElementTestUtil.DXA_GOAL, SAMPLE.getDxaGoal());
		assertEquals(ParagraphElementTestUtil.DYA_GOAL, SAMPLE.getDyaGoal());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(ParagraphElementTestUtil.SERIALIZED_PICTURE_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		Picture instance =
				MarshalTestUtil.unmarshal(ParagraphElementTestUtil.SERIALIZED_PICTURE_RESOURCE, Picture.class);
		ParagraphElementTestUtil.assertPictureEquals(SAMPLE, instance);
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("mime: image/jpeg; hor. scale fact: 0.123; vert. scale factor: 1.0; DxaGoalMm: 30; DyaGoalMm: "
				+ "40; base64Content: 'c2FtcGxlIG...'", SAMPLE.toString());
	}
}
