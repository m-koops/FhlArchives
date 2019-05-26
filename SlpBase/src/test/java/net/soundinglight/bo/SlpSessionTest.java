/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class SlpSessionTest {
	private static final List<Track> TRACKS = Arrays.asList(TrackTest.SAMPLE);
	private static final String TRACK_TIMING = "some track timings";
	private static final List<String> TRACK_TIMINGS = Arrays.asList(TRACK_TIMING);
	public static final SlpSession SAMPLE = new SlpSession(SessionDetailsTest.SAMPLE_AVAILABLE, TRACKS,
			TRACK_TIMINGS);
	private static final String SERIALIZED_RESOURCE = "../bo/serializedSession.xml";

	@Test
	public void testGetters() {
		assertSame(SessionDetailsTest.SAMPLE_AVAILABLE, SAMPLE.getDetails());
		assertSame(TRACKS, SAMPLE.getTracks());
		assertSame(TRACK_TIMINGS, SAMPLE.getTrackTimings());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		SessionTestUtil.assertSessionEquals(SAMPLE,
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, SlpSession.class));
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("1: 04/22/1982; MORNING1; Session 1 title; 1 tracks; 1 timings",
				SAMPLE.toString());
	}
}
