/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo.correction;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.poi.bo.DocumentCorrectionsTestUtil;
import net.soundinglight.poi.bo.DocumentTestUtil;
import net.soundinglight.poi.bo.correction.Removal;

public class RemovalTest {
	private static final String SERIALIZED_REPLACEMENT_RESOURCE = "../poi/bo/serializedRemoval.xml";
	public static final Removal SAMPLE = new Removal(1, 2);

	@Test
	public void testGetters() {
		assertEquals(1, SAMPLE.getFrom());
		assertEquals(2, SAMPLE.getTo());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_REPLACEMENT_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		Removal instance = MarshalTestUtil.unmarshal(SERIALIZED_REPLACEMENT_RESOURCE, Removal.class);
		DocumentCorrectionsTestUtil.assertRemovalEquals(SAMPLE, instance);
	}

	@Test
	public void testApplyShouldRemoveSelectedParagraphs() throws Exception {
		Document document = DocumentTestUtil.createTestDocument();
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2);
		DocumentCorrectionsTestUtil.assertCorrectionApplied(document, SAMPLE);
	}

	@Test
	public void testApplyShouldRemoveSingleParagraph() throws Exception {
		Document document = DocumentTestUtil.createTestDocument();
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2);
		DocumentCorrectionsTestUtil.assertCorrectionApplied(document, new Removal(1, 1), 2);
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("remove paragraphs 1-2", SAMPLE.toString());
	}
}
