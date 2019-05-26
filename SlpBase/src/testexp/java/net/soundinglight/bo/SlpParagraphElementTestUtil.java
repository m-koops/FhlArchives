/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import javax.annotation.CheckForNull;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class SlpParagraphElementTestUtil {
	public static void assertElementsEquals(List<? extends SlpParagraphElement> expected,
			List<? extends SlpParagraphElement> actual) {
		assertElementsEquals(null, expected, actual);
	}

	public static void assertElementsEquals(@CheckForNull String message,
			List<? extends SlpParagraphElement> expected, List<? extends SlpParagraphElement> actual) {
		String prefix = message == null ? "" : message + ", ";
		assertEquals(prefix + "element count mismatch", expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertElementEquals(prefix + "element " + i, expected.get(i), actual.get(i));
		}
	}

	public static void assertElementEquals(SlpParagraphElement expected, SlpParagraphElement actual) {
		assertElementEquals(null, expected, actual);
	}

	public static void assertElementEquals(@CheckForNull String message,
			SlpParagraphElement expected, SlpParagraphElement actual) {
		String prefix = message == null ? "" : message + ", ";
		if (expected.isTextFragment()) {
			assertTrue(prefix + "text fragment expected, but found image", actual.isTextFragment());
			TextFragmentTestUtil.assertTextFragmentEquals(expected.asTextFragment(),
					actual.asTextFragment());
		} else {
			assertTrue(prefix + "image expected, but found text fragment", actual.isImage());
			ImageTestUtil.assertImageEquals(expected.asImage(), actual.asImage());
		}
	}

	private SlpParagraphElementTestUtil() {
		// prevent instantiation
	}
}
