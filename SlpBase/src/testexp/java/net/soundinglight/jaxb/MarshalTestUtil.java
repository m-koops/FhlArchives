/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.jaxb;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import net.soundinglight.util.IOUtil;
import net.soundinglight.util.StringUtil;

public final class MarshalTestUtil {

	private MarshalTestUtil() {
		// prevent instantiation
	}

	@SafeVarargs
	public static <T extends Entity> void assertMarshalling(String resource, T instance,
			Class<? extends Entity>... classes) throws JAXBException, IOException {
		String expected = IOUtil.readResourceAsString(Entity.class, resource);
		assertNotNull("resource '" + resource + "' does not exist", expected);
		String actual = StringUtil.normalizeNewLine(MarshalUtil.marshalToString(instance, classes));
		assertEquals(expected, actual);
	}

	public static <T extends Entity> T unmarshal(String resource, Class<T> clz)
			throws JAXBException, IOException {
		String serialized = IOUtil.readResourceAsString(Entity.class, resource);
		assertNotNull("resource '" + resource + "' does not exist", serialized);
		return MarshalUtil.unmarshalFromString(serialized, clz);
	}
}
