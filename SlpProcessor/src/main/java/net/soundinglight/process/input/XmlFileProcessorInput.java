/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.soundinglight.bo.Event;
import net.soundinglight.jaxb.MarshalUtil;
import net.soundinglight.process.ProcessException;

/**
 * {@link SlpProcessorInput} that reads an {@link Event} from XML file.
 */
public class XmlFileProcessorInput implements SlpProcessorInput {

	private InputStream inputStream;

	/**
	 * C'tor.
	 * 
	 * @param file the {@link File} to read the XML from.
	 * @throws FileNotFoundException when file cannot be found.
	 */
	public XmlFileProcessorInput(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}

	/**
	 * C'tor.
	 * 
	 * @param inputStream the {@link InputStream} to read the XML from.
	 */
	public XmlFileProcessorInput(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event getEvent() throws ProcessException {
		try {
			return MarshalUtil.unmarshalFromStream(inputStream, Event.class);
		} catch (JAXBException | IOException e) {
			throw new ProcessException("failed to load the event from file", e);
		}
	}
}
