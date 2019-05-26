/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.CheckForNull;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.poi.bo.correction.Addition;
import net.soundinglight.poi.bo.correction.Correction;
import net.soundinglight.poi.bo.correction.Move;
import net.soundinglight.poi.bo.correction.Removal;
import net.soundinglight.poi.bo.correction.Replacement;

public final class DocumentCorrectionsTestUtil {

	public static void assertReplacementEquals(Replacement expected, Replacement actual) {
		assertReplacementEquals(null, expected, actual);
	}

	public static void assertReplacementEquals(@CheckForNull String message, Replacement expected, Replacement actual) {
		String prefix = message == null ? "" : message + ", ";

		ParagraphTestUtil.assertParagraphEquals(prefix + "replacement", expected.getReplacementParagraph(),
				actual.getReplacementParagraph());
	}

	public static void assertAdditionEquals(Addition expected, Addition actual) {
		assertAdditionEquals(null, expected, actual);
	}

	public static void assertAdditionEquals(@CheckForNull String message, Addition expected, Addition actual) {
		String prefix = message == null ? "" : message + ", ";

		assertEquals(prefix + "after", expected.getAfter(), actual.getAfter());

		List<Paragraph> expectedParagraphs = expected.getAdditionalParagraphs();
		List<Paragraph> actualParagraphs = actual.getAdditionalParagraphs();
		ParagraphTestUtil.assertParagraphsEquals(prefix + "paragraphs", expectedParagraphs, actualParagraphs);
	}

	public static void assertRemovalEquals(Removal expected, Removal actual) {
		assertRemovalEquals(null, expected, actual);
	}

	public static void assertRemovalEquals(@CheckForNull String message, Removal expected, Removal actual) {
		String prefix = message == null ? "" : message + ", ";

		assertEquals(prefix + "from", expected.getFrom(), actual.getFrom());
		assertEquals(prefix + "to", expected.getTo(), actual.getTo());
	}

	public static void assertMoveEquals(Move expected, Move actual) {
		assertMoveEquals(null, expected, actual);
	}

	public static void assertMoveEquals(@CheckForNull String message, Move expected, Move actual) {
		String prefix = message == null ? "" : message + ", ";

		assertEquals(prefix + "id", expected.getId(), actual.getId());
		assertEquals(prefix + "to", expected.getAfter(), actual.getAfter());
	}

	public static void assertDocumentCorrectionsEquals(DocumentCorrections expected, DocumentCorrections actual) {
		List<Correction> expectedCorrections = expected.getCorrections();
		List<Correction> actualCorrections = actual.getCorrections();
		assertEquals(expectedCorrections.size(), actualCorrections.size());
		for (int i = 0; i < expectedCorrections.size(); i++) {
			assertCorrectionEquals("correction " + i, expectedCorrections.get(i), actualCorrections.get(i));
		}
	}

	private static void assertCorrectionEquals(String message, Correction expected, Correction actual) {
		Class<? extends Correction> correctionCls = expected.getClass();
		assertEquals(message, correctionCls.getName(), actual.getClass().getName());
		if (correctionCls.equals(Removal.class)) {
			assertRemovalEquals(message, (Removal)expected, (Removal)actual);
			return;
		}

		if (correctionCls.equals(Addition.class)) {
			assertAdditionEquals(message, (Addition)expected, (Addition)actual);
			return;
		}

		if (correctionCls.equals(Move.class)) {
			assertMoveEquals(message, (Move)expected, (Move)actual);
			return;
		}

		assertReplacementEquals(message, (Replacement)expected, (Replacement)actual);
	}

	public static void assertCorrectionApplied(Document document, Correction correction, int... expectedParagraphIds)
			throws PoiInputException {
		ParagraphTestUtil.assertParagraphIds(correction.apply(document.getParagraphs()), expectedParagraphIds);
	}

	private DocumentCorrectionsTestUtil() {
		// prevent instantiation
	}
}
