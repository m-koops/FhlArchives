/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.output;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBException;

import net.soundinglight.bo.Event;
import net.soundinglight.jaxb.MarshalUtil;
import net.soundinglight.process.ProcessException;

/**
 * {@link SlpProcessorOutput} that marshals the provided {@link Event} to XML file.
 * 
 */
public class XmlFileProcessorOutput implements SlpProcessorOutput {
	private final File outputFile;

	/**
	 * C'tor.
	 * 
	 * @param outputFile the {@link File} to output to.
	 */
	public XmlFileProcessorOutput(File outputFile) {
		this.outputFile = outputFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEvent(Event event) throws ProcessException {
		try {
			MarshalUtil.marshalToFile(event, outputFile);
		} catch (IOException | JAXBException e) {
			throw new ProcessException("failed to write the event to file", e);
		}
	}
}
