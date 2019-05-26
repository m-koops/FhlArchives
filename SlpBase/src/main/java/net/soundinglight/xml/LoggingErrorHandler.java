/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.xml;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * An error handler that uses JDK logging.
 */
public final class LoggingErrorHandler implements ErrorHandler, ErrorListener {
	public static final LoggingErrorHandler INSTANCE = new LoggingErrorHandler();
	private static final Logger LOG = Logger.getLogger(LoggingErrorHandler.class.getName());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void error(SAXParseException e) throws SAXException {
		LOG.log(Level.SEVERE, getMessageAndLocation(e), e);
		throw e;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		LOG.log(Level.SEVERE, getMessageAndLocation(e), e);
		throw e;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void warning(SAXParseException e) {
		LOG.log(Level.WARNING, getMessageAndLocation(e), e);
	}

	private String getMessageAndLocation(SAXParseException e) {
		return e.getMessage() + "; SystemID: " + e.getSystemId() + "; PublicID: " + e.getPublicId()
				+ "; Line#: " + e.getLineNumber() + "; Column#: " + e.getColumnNumber();
	}

	private String getMessageAndLocation(TransformerException e) {
		SourceLocator locator = e.getLocator();
		if (locator == null) {
			return e.getMessage();
		}
		return e.getMessage() + "; SystemID: " + locator.getSystemId() + "; PublicID: "
				+ locator.getPublicId() + "; Line#: " + locator.getLineNumber() + "; Column#: "
				+ locator.getColumnNumber();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void error(TransformerException e) throws TransformerException {
		LOG.log(Level.SEVERE, getMessageAndLocation(e), e);
		throw e;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fatalError(TransformerException e) throws TransformerException {
		LOG.log(Level.SEVERE, getMessageAndLocation(e), e);
		throw e;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void warning(TransformerException e) {
		LOG.log(Level.WARNING, getMessageAndLocation(e), e);
	}

	LoggingErrorHandler() {
	}
}
