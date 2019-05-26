/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo.media;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PalVideoDetailsTest {
	private static final PalVideoDetails SAMPLE = new PalVideoDetails("1", "Restricted");
	private static final String SERIALIZED_RESOURCE = "../bo/media/serializedPalVideoDetails.xml";

	@Test
	public void testGetters() {
		assertEquals("1", SAMPLE.getId());
		assertEquals("Restricted", SAMPLE.getRestriction());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		PalVideoDetails instance =
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, PalVideoDetails.class);
		assertEquals("1", instance.getId());
		assertEquals("Restricted", instance.getRestriction());
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("1 (Restricted)", SAMPLE.toString());
	}
}
