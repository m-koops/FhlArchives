/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.jaxb;

import static net.soundinglight.AssertExt.*;
import static org.junit.Assert.*;

import javax.xml.bind.annotation.XmlType;

import org.junit.Test;

public class MarshallerFactoryTest {
	@XmlType
	private static class ToMarshall {
		// empty
	}

	@Test
	public void testCreateMarshaller() throws Exception {
		assertNotNull(MarshallerFactory.createMarshaller(ToMarshall.class));
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(MarshallerFactory.class);
	}

}
