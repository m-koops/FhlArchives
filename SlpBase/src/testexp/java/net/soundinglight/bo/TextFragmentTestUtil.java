/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import javax.annotation.CheckForNull;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class TextFragmentTestUtil {

	public static final TextFragment EMPTY = new TextFragment("");
	public static final TextFragment NORMAL = new TextFragment(TextType.LATIN, TextStyle.NORMAL,
			"latin");
	public static final TextFragment ITALIC = new TextFragment(TextType.LATIN, TextStyle.ITALIC,
			"italic");
	public static final TextFragment SACRED = new TextFragment(TextType.SACRED, TextStyle.NORMAL,
			"sacred");
	public static final TextFragment BOLD_WHITESPACE = new TextFragment(TextType.LATIN,
			TextStyle.BOLD, "\t");

	public static void assertTextFragmentsEquals(List<TextFragment> expected,
			List<TextFragment> actual) {
		assertTextFragmentsEquals(null, expected, actual);
	}

	public static void assertTextFragmentsEquals(@CheckForNull String message,
			List<TextFragment> expected, List<TextFragment> actual) {
		String prefix = message == null ? "" : message + ", ";
		assertEquals(prefix + "fragment count mismatch", expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertTextFragmentEquals(prefix + "fragment " + i, expected.get(i), actual.get(i));
		}
	}

	public static void assertTextFragmentEquals(TextFragment expected, TextFragment actual) {
		assertTextFragmentEquals(null, expected, actual);
	}

	public static void assertTextFragmentEquals(@CheckForNull String message,
			TextFragment expected, TextFragment actual) {
		assertTextFragment(message, actual, expected.getType(), expected.getStyle(),
				expected.getText());
	}

	public static void assertTextFragment(TextFragment actual, TextType textType,
			TextStyle textStyle, String text) {
		assertTextFragment(null, actual, textType, textStyle, text);
	}

	public static void assertTextFragment(@CheckForNull String message, TextFragment actual,
			TextType textType, TextStyle textStyle, String text) {
		String prefix = message == null ? "" : message + ", ";

		assertEquals(prefix + "texttype mismatch", textType, actual.getType());
		assertEquals(prefix + "textstyle mismatch", textStyle, actual.getStyle());
		assertEquals(prefix + "text mismatch", text, actual.getText());
	}

	private TextFragmentTestUtil() {
		// prevent instantiation
	}
}
