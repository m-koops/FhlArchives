/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.CheckForNull;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Utility to safely close items.
 * 
 */
public final class CloseUtil {
	private static final Logger LOG = Logger.getLogger(CloseUtil.class.getName());

	/**
	 * Safely close an {@link InputStream}.
	 * 
	 * @param is the stream to close.
	 */
	public static void safeClose(@CheckForNull InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "failed to close input stream", e);
			}
		}
	}

	/**
	 * Safely close a source.
	 * 
	 * @param source the source to close.
	 */
	public static void safeClose(@CheckForNull Source source) {
		if (source == null) {
			return;
		}

		assert source instanceof StreamSource;
		safeClose(((StreamSource)source).getInputStream());
	}

	/**
	 * Safely close a {@link Closeable} item.
	 * 
	 * @param closeable the item to close.
	 */
	public static void safeClose(@CheckForNull Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				LOG.log(Level.SEVERE, "failed to close", e);
			}
		}
	}

	private CloseUtil() {
		// prevent instantiation
	}
}
