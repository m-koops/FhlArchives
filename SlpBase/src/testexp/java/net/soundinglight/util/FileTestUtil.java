/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static org.junit.Assert.fail;

import java.io.File;

public final class FileTestUtil {

	/**
	 * Get a test resource as a {@link File} pointing to the resource in its source folder. This
	 * allows test classes to overwrite the content of the file to update expected values. <br/>
	 * Fails when the resource does not exist.
	 * 
	 * @param clz context class to resolve to.
	 * @param resource the resource to resolve.
	 * 
	 * @return the resource source file.
	 */
	public static File getTestResourceAsFile(Class<?> clz, String resource) {
		File srcRoot = FileUtil.getSrcRoot(clz);
		String fullName = FileUtil.getFullResourcePath(clz, resource);
		File testResources = new File(srcRoot, "test/resources");
		File testExpResources = new File(srcRoot, "testexp/resources");

		File result = new File(testResources, fullName);
		if (result.exists()) {
			return result;
		}

		result = new File(testExpResources, fullName);
		if (result.exists()) {
			return result;
		}

		if (testExpResources.exists()) {
			return new File(testExpResources, fullName);
		}

		if (testResources.exists()) {
			return new File(testResources, fullName);
		}

		fail("both folders test/resources and testexp/resources do not exist");
		return null;
	}

	private FileTestUtil() {
		// prevent instantiation
	}
}
