/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import java.io.*;

import static org.junit.Assert.fail;

public final class IOTestUtil {

	/**
	 * Read a {@link File} as {@link String}.
	 * 
	 * @param file the file to read from.
	 * @return the {@link String} content of the file.
	 */
	public static String readFileAsString(File file) {
		try {
			return readStreamAsString(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			fail("failed to find file '" + file.getPath() + "': " + e.getMessage());
			return null;
		}
	}

	/**
	 * Write a {@link String} to {@link File}.
	 * 
	 * @param content the {@link String} to write.
	 * @param file the {@link File} to write to.
	 */
	public static void writeStringToFile(String content, File file) {
		try {
			IOUtil.writeStringToStream(new FileOutputStream(file), content);
		} catch (IOException e) {
			fail("failed to write string to file: " + e.getMessage());
		}
	}

	private static String readStreamAsString(InputStream is) {
		try {
			return IOUtil.readStreamAsString(is);
		} catch (IOException e) {
			fail("failed to read resource as string: " + e.getMessage());
			return null;
		}
	}

	private IOTestUtil() {
		// prevent instantiation
	}

}
