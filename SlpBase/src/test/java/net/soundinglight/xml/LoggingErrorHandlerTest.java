/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.xml;

import static org.mockito.Mockito.*;

import java.util.logging.Level;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

import net.soundinglight.LogStore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXParseException;

public class LoggingErrorHandlerTest {
	private static final SAXParseException SAX_PARSE_EXCEPTION = new SAXParseException(
			"deliberate", "publicId", "systemId", 1, 3);

	private static final TransformerException TRANSFORMER_EXCEPTION = createTransformerException();

	private static final String LOG_MESSAGE =
			"deliberate; SystemID: systemId; PublicID: publicId; Line#: 1; Column#: 3";

	private LoggingErrorHandler handler = LoggingErrorHandler.INSTANCE;

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Rule
	public final LogStore store = new LogStore();

	@Test
	public void testErrorShouldLogAndThrowSAXParseException() throws Exception {
		store.expectMsg(Level.SEVERE, LOG_MESSAGE);
		thrown.expect(SAXParseException.class);
		thrown.expectMessage("deliberate");

		handler.error(SAX_PARSE_EXCEPTION);
	}

	@Test
	public void testFatalErrorShouldLogAndThrowSAXParseException() throws Exception {
		store.expectMsg(Level.SEVERE, LOG_MESSAGE);
		thrown.expect(SAXParseException.class);
		thrown.expectMessage("deliberate");

		handler.fatalError(SAX_PARSE_EXCEPTION);
	}

	@Test
	public void testWarningShouldLogButNotThrowTransformerException() {
		store.expectMsg(Level.WARNING, LOG_MESSAGE);
		handler.warning(SAX_PARSE_EXCEPTION);
	}

	@Test
	public void testErrorShouldLogAndThrowTransformerException() throws Exception {
		store.expectMsg(Level.SEVERE, LOG_MESSAGE);
		thrown.expect(TransformerException.class);
		thrown.expectMessage("deliberate");

		handler.error(TRANSFORMER_EXCEPTION);
	}

	@Test
	public void testFatalErrorShouldLogAndThrowTransformerException() throws Exception {
		store.expectMsg(Level.SEVERE, LOG_MESSAGE);
		thrown.expect(TransformerException.class);
		thrown.expectMessage("deliberate");

		handler.fatalError(TRANSFORMER_EXCEPTION);
	}

	@Test
	public void testWarningShouldLogButNotThrowSAXParseException() {
		store.expectMsg(Level.WARNING, LOG_MESSAGE);
		handler.warning(TRANSFORMER_EXCEPTION);
	}

	@Test
	public void testErrorShouldHandleMissingLocationProperly() throws Exception {
		store.expectMsg(Level.SEVERE, "deliberate");
		thrown.expect(TransformerException.class);
		thrown.expectMessage("deliberate");

		handler.error(new TransformerException("deliberate"));
	}

	private static TransformerException createTransformerException() {
		SourceLocator sourceLocator = mock(SourceLocator.class);
		when(sourceLocator.getSystemId()).thenReturn("systemId");
		when(sourceLocator.getPublicId()).thenReturn("publicId");
		when(Integer.valueOf(sourceLocator.getLineNumber())).thenReturn(Integer.valueOf(1));
		when(Integer.valueOf(sourceLocator.getColumnNumber())).thenReturn(Integer.valueOf(3));
		return new TransformerException("deliberate", sourceLocator);
	}
}
