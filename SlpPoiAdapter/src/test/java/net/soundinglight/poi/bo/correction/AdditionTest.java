/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo.correction;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.poi.bo.DocumentCorrectionsTestUtil;
import net.soundinglight.poi.bo.DocumentTestUtil;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.poi.bo.ParagraphTestUtil;

public class AdditionTest {
	private static final String SERIALIZED_ADDITION_RESOURCE = "../poi/bo/serializedAddition.xml";
	private static final Paragraph ADDITION_PARAGRAPH = ParagraphTestUtil.createParagraph(-1);
	public static final Addition SAMPLE = new Addition(1, Collections.singletonList(ADDITION_PARAGRAPH));

	@Test
	public void testGetters() {
		assertEquals(1, SAMPLE.getAfter());
		assertEquals(Collections.singletonList(ADDITION_PARAGRAPH), SAMPLE.getAdditionalParagraphs());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_ADDITION_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		Addition instance = MarshalTestUtil.unmarshal(SERIALIZED_ADDITION_RESOURCE, Addition.class);
		DocumentCorrectionsTestUtil.assertAdditionEquals(SAMPLE, instance);
	}

	@Test
	public void testApplyShouldAddAfterFirstParagraph() throws Exception {
		Document document = DocumentTestUtil.createTestDocument();
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2);
		DocumentCorrectionsTestUtil.assertCorrectionApplied(document, SAMPLE, 1, -1, 2);
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2);
	}

	@Test
	public void testApplyShouldAddAtStartOfDocument() throws Exception {
		Document document = DocumentTestUtil.createTestDocument();
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2);
		DocumentCorrectionsTestUtil.assertCorrectionApplied(document,
				new Addition(0, Collections.singletonList(ADDITION_PARAGRAPH)), -1, 1, 2);
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals(
				"add paragraphs after 1: [[run1, mime: image/jpeg; hor. scale fact: 0.123; vert. scale factor: "
						+ "1.0; DxaGoalMm: 30; DyaGoalMm: 40; base64Content: 'c2FtcGxlIG...', run2]]",
				SAMPLE.toString());
	}
}
