/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static net.soundinglight.AssertExt.assertEnumValues;
import static org.junit.Assert.assertEquals;

public class TextTypeTest {
	private static final String SERIALIZED_RESOURCE = "../bo/serializedTextType.xml";

	@Test
	public void testGetCssClass() {
		assertEquals("sacred", TextType.SACRED.getCssClass());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, TextType.LATIN);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		assertEquals(TextType.LATIN, MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, TextType.class));
	}

	@Test
	public void testEnumValues() throws Exception {
		assertEnumValues(TextType.class);
	}
}
