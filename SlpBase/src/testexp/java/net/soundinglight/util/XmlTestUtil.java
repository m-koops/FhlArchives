/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public final class XmlTestUtil {
	public static String asString(Document document) throws IOException {
		return asString(document.getDocumentElement());
	}

	public static String asString(Node node) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XmlUtil.copyXml(node, baos, true);
		return StringUtil.normalizeNewLine(IOUtil.toUtf8String(baos));
	}

	private XmlTestUtil() {
		// prevent instantiation
	}
}
