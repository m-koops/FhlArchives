/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * {@link ByteArrayOutputStream} that exposes an {@link InputStream} that yields the bytes written
 * to this stream.
 */
public class DirectByteArrayOutputStream extends ByteArrayOutputStream {
	/**
	 * @return an {@link InputStream} that yields the bytes written to this
	 *         {@link java.io.OutputStream}.
	 */
	public InputStream getInputStream() {
		return new ByteArrayInputStream(toByteArray());
	}

}
