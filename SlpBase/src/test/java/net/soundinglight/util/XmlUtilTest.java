/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static net.soundinglight.AssertExt.assertPrivateCtor;

import static org.junit.Assert.*;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.soundinglight.LogStore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class XmlUtilTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	@Rule
	public final LogStore store = new LogStore();

	@Test
	public void testDocumentCreationAndCopyToStream() throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		XmlUtil.copyXml(createDocument(), os, true);
		String actual = os.toString();
		String expected = IOUtil.readResourceAsString(XmlUtilTest.class, "test.xml");
		assertEquals(expected, actual);
	}

	@Test
	public void testTransformerExceptionIsWrappedToIOException() throws Exception {
		OutputStream os = mock(OutputStream.class);
		doThrow(new RuntimeException("deliberate")).when(os).write(any(byte[].class), anyInt(),
				anyInt());

		store.expectMsg(Level.SEVERE, "deliberate");

		thrown.expect(IOException.class);
		thrown.expectMessage("failed to copy node to output stream");

		XmlUtil.copyXml(createDocument(), os, false);
	}

	private Document createDocument() {
		Document xmlDoc = XmlUtil.createXmlDoc();
		assertNotNull(xmlDoc);
		String nsUri = "urn:slp:tapelist:v1";
		Node node = XmlUtil.createNode(xmlDoc, xmlDoc, nsUri, "root");
		Node attr = XmlUtil.createNode(xmlDoc, node, "@version");
		attr.setTextContent("1.0");
		Node child = XmlUtil.createNode(xmlDoc, node, nsUri, "child");
		child.setTextContent("content");
		return xmlDoc;
	}

	@Test
	public void testTransform() throws Exception {
		assertEquals("test", doTransform("test.xslt"));
	}

	@Test
	public void testTransformShouldHandleTransformFailureProperly() throws Exception {
		thrown.expect(TransformerException.class);
		thrown.expectMessage("Processing terminated by xsl:message");

		doTransform("throwing.xslt");
	}

	private String doTransform(String xslResource) throws TransformerException {
		InputStream xsl = XmlUtilTest.class.getResourceAsStream(xslResource);
		Source src = new StreamSource(XmlUtilTest.class.getResourceAsStream("test.xml"));
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			XmlUtil.transform(src, new StreamResult(baos), createXslSource(xsl, xslResource), true);
			return baos.toString();
		} finally {
			CloseUtil.safeClose(xsl);
			CloseUtil.safeClose(src);
		}
	}

	private StreamSource createXslSource(InputStream xsl, String xslResource) {
		StreamSource source = new StreamSource(xsl);
		source.setSystemId(XmlUtilTest.class.getResource(xslResource).toExternalForm());
		return source;
	}

	@Test
	public void testParseFromString() throws Exception {
		Document doc =
				XmlUtil.parseDocumentFromString("<?xml version=\"1.0\" encoding=\"UTF-8\" "
						+ "standalone=\"no\"?>" + "<root version=\"1.0\" "
						+ "xmlns=\"urn:slp:tapelist:v1\"/>");
		assertEquals("root", doc.getDocumentElement().getNodeName());
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(XmlUtil.class);
	}
}
