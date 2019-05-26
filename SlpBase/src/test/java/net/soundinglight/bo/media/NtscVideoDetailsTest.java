/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo.media;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NtscVideoDetailsTest {
	private static final NtscVideoDetails SAMPLE = new NtscVideoDetails("2", "Unrestricted");
	private static final String SERIALIZED_RESOURCE = "../bo/media/serializedNtscVideoDetails.xml";

	@Test
	public void testGetters() {
		assertEquals("2", SAMPLE.getId());
		assertEquals("Unrestricted", SAMPLE.getRestriction());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		NtscVideoDetails instance =
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, NtscVideoDetails.class);
		assertEquals("2", instance.getId());
		assertEquals("Unrestricted", instance.getRestriction());
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("2 (Unrestricted)", SAMPLE.toString());
	}
}
