/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

import net.soundinglight.util.IOUtil;

public class ScratchPath implements Closeable {
	private File path;

	public ScratchPath(Class<?> clz) {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		path = new File(tempDir, "net.soundinglight/" + clz.getSimpleName());
		IOUtil.createDir(path);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		IOUtil.delete(path);
	}

	public File createAbstractFile(String subPath) {
		return new File(path, subPath);
	}
}
