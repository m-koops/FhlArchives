/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.CheckForNull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.saxon.TransformerFactoryImpl;
import net.soundinglight.SlpConstants;
import net.soundinglight.core.CallableT;
import net.soundinglight.core.UnreachableUtil;
import net.soundinglight.io.StringInputStream;
import net.soundinglight.xml.LoggingErrorHandler;

/**
 * XML related utility methods.
 *
 */
public final class XmlUtil {
	private static final String IDENTITY_XSLT = SlpConstants.BASE_PACKAGE_PATH + "identity.xslt";
	private static final TransformerFactory TRANSFORMER_FACTORY = createTransformerFactory();

	private static TransformerFactory createTransformerFactory() {
		TransformerFactory factory = new TransformerFactoryImpl();
		factory.setURIResolver(new LocalResourceResolver());
		return factory;
	}

	private static final Logger LOG = Logger.getLogger(XmlUtil.class.getName());
	private static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = createDocumentFactory();

	private static DocumentBuilderFactory createDocumentFactory() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		return factory;
	}

	private static DocumentBuilder createXmlDocumentBuilder() {
		CallableT<DocumentBuilder, ParserConfigurationException> callable =
				new CallableT<DocumentBuilder, ParserConfigurationException>() {
					@Override
					@SuppressWarnings("synthetic-access")
					public DocumentBuilder call() throws ParserConfigurationException {
						return DOCUMENT_BUILDER_FACTORY.newDocumentBuilder();
					}
				};
		return UnreachableUtil.suppressException(callable);
	}

	/**
	 * Create a new XML document.
	 *
	 * @return the XML document.
	 */
	@CheckForNull
	public static Document createXmlDoc() {
		return createXmlDocumentBuilder().newDocument();
	}

	/**
	 * create a node in the name-space of the parent node.
	 *
	 * @param doc the document to create the node in.
	 * @param parent the parent node.
	 * @param name the name of the node.
	 * @return the created node.
	 */
	public static Node createNode(Document doc, Node parent, String name) {
		String nsUri = parent.getNamespaceURI();
		return createNode(doc, parent, nsUri, name);
	}

	/**
	 * create a node in a specific name-space.
	 *
	 * @param doc the document to create the node in.
	 * @param parent the parent node.
	 * @param nsUri the name-space URI of the node.
	 * @param name the name of the node.
	 * @return the created node.
	 */
	public static Node createNode(Document doc, Node parent, String nsUri, String name) {
		if (name.startsWith("@")) {
			Node attr = doc.createAttributeNS(null, name.substring(1));
			parent.getAttributes().setNamedItem(attr);
			return attr;
		}

		Node elem = doc.createElementNS(nsUri, name);
		parent.appendChild(elem);
		return elem;
	}

	/**
	 * Copy an XML node to an output stream.
	 *
	 * @param node node to copy
	 * @param os output stream to copy to
	 * @param indent whether or not to indent the output.
	 * @throws IOException on I/O failures
	 */
	public static void copyXml(Node node, OutputStream os, boolean indent) throws IOException {
		InputStream xsl = XmlUtil.class.getResourceAsStream(IDENTITY_XSLT);
		try {
			transform(new DOMSource(node), new StreamResult(new OutputStreamWriter(os, "UTF-8")), new StreamSource(xsl),
					indent);
		} catch (TransformerException e) {
			throw new IOException("failed to copy node to output stream", e);
		} finally {
			CloseUtil.safeClose(xsl);
		}
	}

	/**
	 * Apply a transformation from a source to a result.
	 *
	 * @param src source
	 * @param res result
	 * @param template the XSL template to apply
	 * @param indent whether or not to indent the output.
	 * @throws TransformerException on transform failures
	 */
	public static void transform(Source src, Result res, Source template, boolean indent) throws TransformerException {
		try {
			Transformer transformer = TRANSFORMER_FACTORY.newTransformer(template);
			transformer.setErrorListener(LoggingErrorHandler.INSTANCE);

			if (indent) {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			}

			transformer.transform(src, res);
		} catch (RuntimeException e) {
			LOG.log(Level.SEVERE, "xsl transform failed", e);
			throw new TransformerException(e);
		} finally {
			CloseUtil.safeClose(template);
		}
	}

	/**
	 * Parse a {@link Document} from a {@link String}.
	 *
	 * @param xml the {@link String} to read.
	 * @return the parsed {@link Document}.
	 * @throws SAXException on failure.
	 * @throws IOException on failure.
	 */
	public static Document parseDocumentFromString(String xml) throws SAXException, IOException {
		return parseDocument(new StringInputStream(xml));
	}

	/**
	 * Parse a {@link Document} from an {@link InputStream}.
	 *
	 * @param is the {@link InputStream} to read.
	 * @return the parsed {@link Document}.
	 * @throws SAXException on failure.
	 * @throws IOException on failure.
	 */
	public static Document parseDocument(InputStream is) throws SAXException, IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		try {
			return createXmlDocumentBuilder().parse(new InputSource(bis));
		} finally {
			CloseUtil.safeClose(bis);
		}
	}

	private XmlUtil() {
		// prevent instantiation
	}
}
