/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.CheckForNull;

/**
 * Utility handling files.
 *
 */
public final class FileUtil {
	private static final Logger LOG = Logger.getLogger(FileUtil.class.getName());

	/**
	 * Returns the specified resource as file.
	 *
	 * @param clazz the class used for resource resolution.
	 * @param resource the resource path.
	 * @return the resource file.
	 */
	public static File getResource(Class<?> clazz, String resource) {
		File file = findResource(clazz, resource);
		assert file != null : "missing resource " + resource + " in context of " + clazz;
		return file;
	}

	/**
	 * Returns the specified main-line resource as file, or <code>null</code> if not found.
	 *
	 * @param clazz the class used for resource resolution.
	 * @param resource the resource path.
	 * @param createIfMissing create an empty resource file if it does not exist yet.
	 * @return the resource file if found or created, otherwise <code>null</code>.
	 * @throws IOException on failure.
	 */
	@CheckForNull
	public static File getMainResource(Class<?> clazz, String resource, boolean createIfMissing) throws IOException {
		File srcRoot = FileUtil.getSrcRoot(clazz);
		String fullName = FileUtil.getFullResourcePath(clazz, resource);
		File resources = new File(srcRoot, "main/resources");

		File result = new File(resources, fullName);
		if (result.exists()) {
			return result;
		}

		if (createIfMissing) {
			Files.createFile(result.toPath());
			return result;
		}

		return null;
	}

	/**
	 * Returns the specified resource as file or <code>null</code> if the resource could not be
	 * found.
	 *
	 * @param clazz the class used for resource resolution.
	 * @param resource the resource URL.
	 * @return a file or <code>null</code> if resource not found.
	 */
	@CheckForNull
	public static File findResource(Class<?> clazz, String resource) {
		URL url = clazz.getResource(resource);
		return url == null ? null : new File(url.getFile());
	}

	/**
	 * Get a normalized path for the provide {@link File}. The file path will be normalized to *nix
	 * format.
	 *
	 * @param file the {@link File} to get the normalized path of.
	 * @return the normalized path.
	 */
	static String getNormalizedPath(File file) {
		return file.getPath().replace('\\', '/');
	}

	/**
	 * Gets the root 'src' directory which contains the specified class.
	 *
	 * @param clz a file in the source tree
	 * @return the source root as file
	 */
	static File getSrcRoot(Class<?> clz) {
		File classDir = findClassDir(clz);
		String path = classDir.getPath().replaceAll("^file:(?:\\\\|/)", "");
		classDir = new File(path);

		return getSrcRoot(classDir);
	}

	private static File findClassDir(Class<?> clz) {
		String file = clz.getResource(clz.getSimpleName() + ".class").getFile();
		File classFile = new File(file);
		Pattern pattern = Pattern.compile("\\.|$");
		Matcher matcher = pattern.matcher(clz.getName());
		while (matcher.find()) {
			classFile = classFile.getParentFile();
		}
		return classFile;
	}

	/**
	 * Gets the root 'src' directory which contains the specified file.
	 *
	 * @param start a file in the source tree
	 * @return the source root as file
	 */
	@CheckForNull
	static File getSrcRoot(File start) {
		File parent = start;
		while (true) {
			File src = new File(parent, "src");
			if (src.exists()) {
				return src;
			}
			parent = parent.getParentFile();
			assert parent != null : "src root not found; started from " + getNormalizedPath(start);
		}
	}

	/**
	 * Expand the path of resource against a given Class.
	 *
	 * @param clz the Class to resolve the local resource path.
	 * @param resource the local resource path.
	 * @return the resolved full path.
	 */
	public static String getFullResourcePath(Class<?> clz, String resource) {
		if (resource.startsWith("/")) {
			return resource.substring(1);
		}

		return clz.getPackage().getName().replace(".", "/") + "/" + resource;
	}

	private FileUtil() {
		// prevent instantiation
	}

}
