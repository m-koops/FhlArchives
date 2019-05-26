package net.soundinglight.poi.bo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DocumentTestUtil {
	static final Paragraph PARAGRAPH1 = ParagraphTestUtil.createParagraph(1);
	static final Paragraph PARAGRAPH2 = new Paragraph(2, 234, 567,
			ParagraphElementTestUtil.createItalicCharRun("italic"), ParagraphElementTestUtil.createBoldCharRun("bold"));
	static final String NAME = "test document";

	public static Document createTestDocument() {
		return new Document(NAME, Arrays.asList(PARAGRAPH1, PARAGRAPH2));
	}

	public static Document createTestDocument(int... ids) {
		ArrayList<Paragraph> paragraphs = new ArrayList<>(ids.length);
		for (int id : ids) {
			paragraphs.add(ParagraphTestUtil.createParagraph(id));
		}
		return new Document("test document", paragraphs);
	}

	static void assertDocumentEquals(Document expected, Document actual) {
		List<Paragraph> expectedParagraphs = expected.getParagraphs();
		List<Paragraph> actualParagraphs = actual.getParagraphs();
		assertEquals(expectedParagraphs.size(), actualParagraphs.size());
		for (int i = 0; i < expectedParagraphs.size(); i++) {
			ParagraphTestUtil.assertParagraphEquals(expectedParagraphs.get(i), actualParagraphs.get(i));
		}
	}

	public static void assertParagraphIdsInDocument(Document document, int... ids) {
		ParagraphTestUtil.assertParagraphIds(document.getParagraphs(), ids);
	}

	private DocumentTestUtil() {
		// prevent instantiation
	}
}
