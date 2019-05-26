/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.poi.bo.correction.Addition;
import net.soundinglight.poi.bo.correction.AdditionTest;
import net.soundinglight.poi.bo.correction.Correction;
import net.soundinglight.poi.bo.correction.Move;
import net.soundinglight.poi.bo.correction.Removal;
import net.soundinglight.poi.bo.correction.Replacement;

public class DocumentCorrectionsTest {
	private static final int LAST_APPLIED_PARAGRAPH_ID = -2;
	private static final int NEXT_PARAGRAPH_ID = LAST_APPLIED_PARAGRAPH_ID - 1;
	private static final Addition ADDITION = new Addition(2,
			Arrays.asList(ParagraphTestUtil.createParagraph(-1), ParagraphTestUtil.createParagraph(-2)));
	private static final Removal REMOVAL = new Removal(3, 4);
	private static final Replacement REPLACEMENT = new Replacement(ParagraphTestUtil.createParagraph(2));
	private static final Move MOVE = new Move(5, 2);
	private static final List<Correction> CORRECTIONS = Arrays.asList(ADDITION, REMOVAL, REPLACEMENT, MOVE);
	private static final String SERIALIZED_CORRECTIONS_RESOURCE = "../poi/bo/serializedDocumentCorrections.xml";
	public static final DocumentCorrections SAMPLE = createDocumentCorrections();

	private static DocumentCorrections createDocumentCorrections() {
		DocumentCorrections result = new DocumentCorrections(LAST_APPLIED_PARAGRAPH_ID);
		CORRECTIONS.stream().forEach(c -> result.add(c));
		return result;
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetters() {
		assertEquals(CORRECTIONS, SAMPLE.getCorrections());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_CORRECTIONS_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		DocumentCorrections instance =
				MarshalTestUtil.unmarshal(SERIALIZED_CORRECTIONS_RESOURCE, DocumentCorrections.class);
		DocumentCorrectionsTestUtil.assertDocumentCorrectionsEquals(SAMPLE, instance);
		assertEquals(NEXT_PARAGRAPH_ID, instance.getNextParagraphId());
	}

	@Test
	public void testShouldApplyCorrections() throws Exception {
		Document document = DocumentTestUtil.createTestDocument(1, 2, 3, 4, 5);
		DocumentTestUtil.assertParagraphIdsInDocument(document, 1, 2, 3, 4, 5);

		// 1, 2, 3, 4, 5 -> original
		// 1, 2, -1, -2, 3, 4, 5 -> after adding -1 and -2 after 2
		// 1, 2, -1, -2, 5 -> after removing 3 - 4
		// 1, 2, -1, -2, 5 -> after replacement of 2
		// 1, 2, 5, -1, -2 -> after move 5 after 2

		ParagraphTestUtil.assertParagraphIds(SAMPLE.apply(document.getParagraphs()), 1, 2, 5, -1, -2);
	}

	@Test
	public void testShouldDecrementAndReturnParagraphId() {
		assertEquals(NEXT_PARAGRAPH_ID, SAMPLE.getNextParagraphId());
		assertEquals(NEXT_PARAGRAPH_ID - 1, SAMPLE.getNextParagraphId());
	}

	@Test
	public void testShouldThrowWhenTryingToAddCorrectionToSingletonNone() {
		thrown.expect(UnsupportedOperationException.class);
		thrown.expectMessage("No changes allowed to singleton NONE");

		DocumentCorrections.NONE.add(AdditionTest.SAMPLE);
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals(
				"[add paragraphs after 2: [[run1, mime: image/jpeg; hor. scale fact: 0.123; vert. scale factor: "
						+ "1.0; DxaGoalMm: 30; DyaGoalMm: 40; base64Content: 'c2FtcGxlIG...', run2], [run1, mime: "
						+ "image/jpeg; hor. scale fact: 0.123; vert. scale factor: 1.0; DxaGoalMm: 30; DyaGoalMm: "
						+ "40; base64Content: 'c2FtcGxlIG...', run2]], remove paragraphs 3-4, replace paragraph 2 "
						+ "with: [run1, mime: image/jpeg; hor. scale fact: 0.123; vert. scale factor: 1.0; "
						+ "DxaGoalMm: 30; DyaGoalMm: 40; base64Content: 'c2FtcGxlIG...', run2], move paragraph 5 after 2]",
				SAMPLE.toString());
	}
}
