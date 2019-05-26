/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import javax.annotation.CheckForNull;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class SessionTestUtil {

	public static void assertSessionsEquals(List<SlpSession> expected, List<SlpSession> actual) {
		assertSessionsEquals(null, expected, actual);
	}

	public static void assertSessionsEquals(@CheckForNull String message, List<SlpSession> expected,
			List<SlpSession> actual) {
		String prefix = message == null ? "" : message + ", ";
		assertEquals(prefix + "session count mismatch", expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertSessionEquals(prefix + "session " + i, expected.get(i), actual.get(i));
		}
	}

	public static void assertSessionEquals(SlpSession expected, SlpSession actual) {
		assertSessionEquals(null, expected, actual);
	}

	public static void assertSessionEquals(@CheckForNull String message, SlpSession expected,
			SlpSession actual) {
		String prefix = message == null ? "" : message + ", ";
		SessionDetailsTestUtil.assertSessionDetailsEquals(prefix, expected.getDetails(),
				actual.getDetails());
		TrackTestUtil.assertTracksEquals(prefix, expected.getTracks(), actual.getTracks());
		assertEquals(prefix + "track timings mismatch", expected.getTrackTimings(),
				actual.getTrackTimings());
	}

	private SessionTestUtil() {
		// prevent instantiation
	}
}
