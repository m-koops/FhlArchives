/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.output;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.soundinglight.bo.Event;
import net.soundinglight.process.ProcessException;
import net.soundinglight.util.CloseUtil;

/**
 * {@link SlpProcessorOutput} that marshals the provided {@link Event} to a HTML file.
 * 
 */
public class HtmlFileProcessorOutput extends HtmlProcessorOutput {
	private final File outputFile;

	/**
	 * C'tor.
	 * 
	 * @param outputFile the {@link File} to output to.
	 */
	public HtmlFileProcessorOutput(File outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEvent(Event event) throws ProcessException {
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(outputFile));
			transformEventToStream(event, os);
		} catch (IOException e) {
			throw new ProcessException("failed to write the event to file", e);
		} finally {
			CloseUtil.safeClose(os);
		}
	}
}
