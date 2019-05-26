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
import net.soundinglight.poi.bo.correction.Move;

public class MoveTest {
	private static final String SERIALIZED_MOVE_RESOURCE = "../poi/bo/serializedMove.xml";
	private static final Move SAMPLE = new Move(1, 2);

	@Test
	public void testGetters() {
		assertEquals(1, SAMPLE.getId());
		assertEquals(2, SAMPLE.getAfter());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_MOVE_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		Move instance = MarshalTestUtil.unmarshal(SERIALIZED_MOVE_RESOURCE, Move.class);
		DocumentCorrectionsTestUtil.assertMoveEquals(SAMPLE, instance);
	}

	@Test
	public void testApplyShouldMoveParagraphToBack() throws Exception {
		Document document = DocumentTestUtil.createTestDocument(1, 2, 3);
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2, 3);
		DocumentCorrectionsTestUtil.assertCorrectionApplied(document, new Move(1, 2), 2, 1, 3);
	}

	@Test
	public void testApplyShouldMoveParagraphToFront() throws Exception {
		Document document = DocumentTestUtil.createTestDocument(1, 2, 3);
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2, 3);
		DocumentCorrectionsTestUtil.assertCorrectionApplied(document, new Move(3, 1), 1, 3, 2);
	}

	@Test
	public void testApplyShouldMoveParagraphToStartOfDocument() throws Exception {
		Document document = DocumentTestUtil.createTestDocument(1, 2, 3);
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2, 3);
		DocumentCorrectionsTestUtil.assertCorrectionApplied(document, new Move(3, 0), 3, 1, 2);
	}

	@Test
	public void testApplyShouldMoveParagraphToItOwnPosition() throws Exception {
		Document document = DocumentTestUtil.createTestDocument(1, 2, 3);
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2, 3);
		DocumentCorrectionsTestUtil.assertCorrectionApplied(document, new Move(2, 2), 1, 2, 3);
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("move paragraph 1 after 2", SAMPLE.toString());
	}
}
