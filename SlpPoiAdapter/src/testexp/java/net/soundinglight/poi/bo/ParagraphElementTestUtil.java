/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import static net.soundinglight.AssertExt.assertBooleanEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.CheckForNull;

import org.apache.commons.codec.Charsets;
import org.apache.poi.hwpf.usermodel.PictureType;

public final class ParagraphElementTestUtil {
	public static final int DYA_GOAL = 40;
	public static final int DXA_GOAL = 30;
	public static final String MIME_TYPE = PictureType.JPEG.getMime();
	public static final String SAMPLE_CONTENT = "sample content";
	public static final float HOR_SCALING_FACT = 0.123f;
	public static final float VERT_SCALING_FACT = 1.000f;
	public static final String SERIALIZED_PICTURE_RESOURCE = "../poi/bo/serializedPicture.xml";

	public static CharacterRun createNormalCharRun(String text) {
		return createRun(text, false, false);
	}

	public static CharacterRun createBoldCharRun(String text) {
		return createRun(text, true, false);
	}

	public static CharacterRun createItalicCharRun(String text) {
		return createRun(text, false, true);
	}

	public static CharacterRun createZapfDingbatsCharRun(String text) {
		return createRun(text, Font.ZAPF_DINGBATS, false, false);
	}

	public static CharacterRun createSlSoulwordCharRun(String text) {
		return createRun(text, Font.SL_SOULWORD, false, false);
	}

	public static CharacterRun createRun(String text, boolean bold, boolean italic) {
		return createRun(text, Font.TIMES, bold, italic);
	}

	public static CharacterRun createRun(String text, Font font, boolean bold, boolean italic) {
		return new CharacterRun(text, font, null, bold, italic);
	}

	public static Picture createTestPictureElement() {
		return new Picture(MIME_TYPE, SAMPLE_CONTENT.getBytes(Charsets.UTF_8), HOR_SCALING_FACT, VERT_SCALING_FACT,
				DXA_GOAL, DYA_GOAL);
	}

	public static void assertParagraphElementsEquals(List<? extends ParagraphElement> expected,
			List<? extends ParagraphElement> actual) {
		assertParagraphElementsEquals(null, expected, actual);
	}

	public static void assertParagraphElementsEquals(@CheckForNull String message,
			List<? extends ParagraphElement> expected, List<? extends ParagraphElement> actual) {
		String prefix = message == null ? "" : message + ", ";

		assertEquals(prefix + "element count mismatch", expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			ParagraphElement expectedElement = expected.get(i);
			if (expectedElement.isCharacterRun()) {
				ParagraphElement actualElement = actual.get(i);
				assertTrue(message, actualElement.isCharacterRun());
				assertCharacterRunEquals(prefix + "run " + i, expectedElement.asCharacterRun(),
						actualElement.asCharacterRun());
				continue;
			}

			assertTrue(message, expectedElement.isPicture());
			ParagraphElement actualElement = actual.get(i);
			assertTrue(message, actualElement.isPicture());
			assertPictureEquals(prefix + "run " + i, expectedElement.asPicture(), actualElement.asPicture());
		}
	}

	public static void assertCharacterRunEquals(CharacterRun expected, CharacterRun actual) {
		assertCharacterRunEquals(null, expected, actual);
	}

	public static void assertCharacterRunEquals(@CheckForNull String message, CharacterRun expected,
			CharacterRun actual) {
		assertCharacterRun(message, actual, expected.getText(), expected.getFont(), expected.isBold(),
				expected.isItalic());
	}

	public static void assertCharacterRun(CharacterRun actual, String text, Font font, boolean bold, boolean italic) {
		assertCharacterRun(null, actual, text, font, bold, italic);
	}

	public static void assertCharacterRun(@CheckForNull String message, CharacterRun actual, String text, Font font,
			boolean bold, boolean italic) {
		String prefix = message == null ? "" : message + ", ";

		assertEquals(prefix + "text mismatch", text, actual.getText());
		assertEquals(prefix + "font mismatch", font, actual.getFont());
		assertBooleanEquals(prefix + "bold mismatch", bold, actual.isBold());
		assertBooleanEquals(prefix + "italic mismatch", italic, actual.isItalic());
	}

	public static void assertPictureEquals(Picture expected, Picture actual) {
		assertPictureEquals(null, expected, actual);
	}

	public static void assertPictureEquals(@CheckForNull String message, Picture expected, Picture actual) {
		String prefix = message == null ? "" : message + ", ";
		assertEquals(prefix + "mime type mismatch", expected.getMimeType(), actual.getMimeType());
		assertArrayEquals(prefix + "base64 content mismatch", expected.getBytes(), actual.getBytes());
	}

	private ParagraphElementTestUtil() {
		// prevent instantiation
	}
}
