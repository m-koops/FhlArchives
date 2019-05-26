/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.soundinglight.util.CloseUtil;

import org.apache.poi.hwpf.HWPFDocument;

public final class HWPFDocumentTestUtil {

	public static HWPFDocument readAsHWPFDocument(String resource) throws IOException {
		InputStream bis = null;
		try {
			InputStream is = HWPFDocumentTestUtil.class.getResourceAsStream(resource);
			assert is != null : "resource '" + resource + "' is not available";
			bis = new BufferedInputStream(is);
			return new HWPFDocument(bis);
		} finally {
			CloseUtil.safeClose(bis);
		}
	}

	private HWPFDocumentTestUtil() {
		// prevent instantiation
	}
}
