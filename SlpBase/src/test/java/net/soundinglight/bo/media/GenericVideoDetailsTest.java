/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo.media;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GenericVideoDetailsTest {
	private static final GenericVideoDetails SAMPLE = new GenericVideoDetails("3", "Restricted");
	private static final String SERIALIZED_RESOURCE =
			"../bo/media/serializedGenericVideoDetails.xml";

	@Test
	public void testGetters() {
		assertEquals("3", SAMPLE.getId());
		assertEquals("Restricted", SAMPLE.getRestriction());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		GenericVideoDetails instance =
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, GenericVideoDetails.class);
		assertEquals("3", instance.getId());
		assertEquals("Restricted", instance.getRestriction());
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("3 (Restricted)", SAMPLE.toString());
	}
}
