/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import javax.annotation.CheckForNull;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public final class ImageTestUtil {
	public static void assertImagesEquals(@CheckForNull String message, List<Image> expected,
			List<Image> actual) {
		String prefix = message == null ? "" : message + ", ";
		assertEquals(prefix + "image count mismatch", expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertImageEquals(prefix + "image " + i, expected.get(i), actual.get(i));
		}
	}

	public static void assertImageEquals(Image expected, Image actual) {
		assertImageEquals(null, expected, actual);
	}

	public static void assertImageEquals(@CheckForNull String message, Image expected, Image actual) {
		assertImage(message, actual, expected.getMimeType(), expected.getBytes());
	}

	public static void assertImage(Image actual, String mimeType, byte[] content) {
		assertImage(null, actual, mimeType, content);
	}

	public static void assertImage(@CheckForNull String message, Image actual,
			String mimeType, byte[] content) {
		String prefix = message == null ? "" : message + ", ";

		assertEquals(prefix + "mimeType mismatch", mimeType, actual.getMimeType());
		assertArrayEquals(prefix + "content mismatch", content, actual.getBytes());
	}

	private ImageTestUtil() {
		// prevent instantiation
	}
}
