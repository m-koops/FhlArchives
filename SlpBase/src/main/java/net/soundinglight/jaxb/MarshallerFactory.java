/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Factory for JAXB marshallers with formatted output.
 * 
 */
public final class MarshallerFactory {

	/**
	 * Creates a JAXB Marshaller.
	 * 
	 * @param classes classes to be bound.
	 * @return the JAXB Marshaller.
	 * @throws JAXBException on failure.
	 */
	public static Marshaller createMarshaller(Class<?>... classes) throws JAXBException {
		Marshaller marshaller = createContext(classes).createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		return marshaller;
	}

	/**
	 * Creates a JAXB Unmarshaller.
	 * 
	 * @param classes classes to be bound.
	 * @return the JAXB Unmarshaller.
	 * @throws JAXBException on failure.
	 */
	public static Unmarshaller createUnmarshaller(Class<?>... classes) throws JAXBException {
		return createContext(classes).createUnmarshaller();
	}

	private static JAXBContext createContext(Class<?>... classes) throws JAXBException {
		return JAXBContext.newInstance(classes);
	}

	private MarshallerFactory() {
		// prevent instantiation
	}
}
