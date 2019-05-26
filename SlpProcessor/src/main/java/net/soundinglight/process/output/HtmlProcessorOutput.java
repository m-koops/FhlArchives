/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.output;

import java.io.OutputStream;

import net.soundinglight.bo.Event;

public abstract class HtmlProcessorOutput implements SlpProcessorOutput {
	private static final String HTML_XSL = "event2html.xslt";

	protected void transformEventToStream(Event event, OutputStream os) {
		new Poi2HtmlTransformer(HtmlFileProcessorOutput.class.getResource(HTML_XSL)).transformToStream(
				event, os);
	}
}
