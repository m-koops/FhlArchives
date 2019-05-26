/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.w3c.dom.Document;

public class XmlTestUtilTest {
	@Test
	public void testOutputShouldBeIndented() throws Exception {
		Document document =
				XmlUtil.parseDocument(XmlTestUtilTest.class.getResourceAsStream("test.xml"));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		XmlUtil.copyXml(document, os, true);
		assertEquals(IOUtil.readResourceAsString(XmlUtilTest.class, "test.xml"),
				IOUtil.toUtf8String(os));
	}
}
