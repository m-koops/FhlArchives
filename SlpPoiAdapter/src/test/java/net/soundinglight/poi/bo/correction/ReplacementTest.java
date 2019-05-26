/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo.correction;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.poi.bo.DocumentCorrectionsTestUtil;
import net.soundinglight.poi.bo.DocumentTestUtil;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.poi.bo.ParagraphTestUtil;

public class ReplacementTest {
	private static final String SERIALIZED_REPLACEMENT_RESOURCE = "../poi/bo/serializedReplacement.xml";
	private static final String SERIALIZED_REPLACEMENT_RESOURCE_OLD = "../poi/bo/serializedReplacementOld.xml";
	private static final Paragraph REPLACEMENT_PARAGRAPH = ParagraphTestUtil.createParagraph(2);
	public static final Replacement SAMPLE = new Replacement(REPLACEMENT_PARAGRAPH);

	@Test
	public void testGetters() {
		assertEquals(REPLACEMENT_PARAGRAPH, SAMPLE.getReplacementParagraph());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_REPLACEMENT_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		Replacement instance = MarshalTestUtil.unmarshal(SERIALIZED_REPLACEMENT_RESOURCE, Replacement.class);
		DocumentCorrectionsTestUtil.assertReplacementEquals(SAMPLE, instance);
	}

	@Test
	public void testUnmarshallingOld() throws Exception {
		Replacement instance = MarshalTestUtil.unmarshal(SERIALIZED_REPLACEMENT_RESOURCE_OLD, Replacement.class);
		DocumentCorrectionsTestUtil.assertReplacementEquals(SAMPLE, instance);
	}

	@Test
	public void testApplyShouldReplaceParagraph() throws Exception {
		Document document = DocumentTestUtil.createTestDocument(1, 2, 3);
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2, 3);
		List<Paragraph> result = SAMPLE.apply(document.getParagraphs());
		ParagraphTestUtil.assertParagraphIds(result, 1, 2, 3);
		ParagraphTestUtil.assertParagraphEquals(SAMPLE.getReplacementParagraph(), result.get(1));
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals(
				"replace paragraph 2 with: [run1, mime: image/jpeg; hor. scale fact: 0.123; vert. scale factor: "
						+ "1.0; DxaGoalMm: 30; DyaGoalMm: 40; base64Content: 'c2FtcGxlIG...', run2]",
				SAMPLE.toString());
	}
}
