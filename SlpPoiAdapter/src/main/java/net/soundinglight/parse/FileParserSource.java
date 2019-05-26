/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.JAXBException;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.jaxb.MarshalUtil;
import net.soundinglight.poi.bo.Document;

/**
 * {@link SlpParserSource} that will read a {@link Document} from file.
 */
public class FileParserSource implements SlpParserSource {
	private final InputStream source;

	/**
	 * C'tor.
	 * 
	 * @param sourceFile the file to read the {@link Document} from.
	 * @throws FileNotFoundException when file not found.
	 */
	public FileParserSource(File sourceFile) throws FileNotFoundException {
		this(new FileInputStream(sourceFile));
	}

	/**
	 * C'tor.
	 * 
	 * @param source the {@link InputStream} to read the {@link Document} from.
	 */
	public FileParserSource(InputStream source) {
		this.source = source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document getDocument() throws PoiInputException {
		try {
			return MarshalUtil.unmarshalFromStream(source, Document.class);
		} catch (JAXBException | IOException e) {
			throw new PoiInputException("failed to unmarshal", e);
		}
	}
}
