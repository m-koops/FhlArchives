/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.output;

import java.io.ByteArrayOutputStream;

import net.soundinglight.bo.Event;
import net.soundinglight.core.FatalProgrammingException;
import net.soundinglight.util.CloseUtil;
import net.soundinglight.util.IOUtil;
import net.soundinglight.util.StringUtil;

/**
 * {@link SlpProcessorOutput} that marshals the provided {@link Event} to a HTML {@link String}.
 * 
 */
public class HtmlStringProcessorOutput extends HtmlProcessorOutput {
	private String html;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEvent(Event event) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			transformEventToStream(event, os);
			html = StringUtil.normalizeNewLine(IOUtil.toUtf8String(os));
		} finally {
			CloseUtil.safeClose(os);
		}
	}

	/**
	 * @return the generated HTML {@link String}.
	 */
	public String getHtml() {
		if (html == null) {
			throw new FatalProgrammingException(
					"first set an event, before getting the resulting HTML");
		}
		return html;
	}
}
