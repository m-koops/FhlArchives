/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import javax.annotation.CheckForNull;
import java.util.List;

import static org.junit.Assert.assertEquals;

public final class TrackTestUtil {

	public static void assertTracksEquals(List<Track> expected, List<Track> actual) {
		assertTracksEquals(null, expected, actual);
	}

	public static void assertTracksEquals(@CheckForNull String message, List<Track> expected,
			List<Track> actual) {
		String prefix = message == null ? "" : message + ", ";
		assertEquals(prefix + "track count mismatch", expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertTrackEquals(prefix + "track " + i, expected.get(i), actual.get(i));
		}
	}

	public static void assertTrackEquals(Track expected, Track actual) {
		assertTrackEquals(null, expected, actual);
	}

	public static void assertTrackEquals(@CheckForNull String message, Track expected, Track actual) {
		assertTrack(message, actual, expected.getDuration(), expected.getNumber(),
				expected.getParagraphs());
	}

	public static void assertTrack(Track track, int duration, @CheckForNull Integer number,
			List<SlpParagraph> paragraphs) {
		assertTrack(null, track, duration, number, paragraphs);
	}

	public static void assertTrack(@CheckForNull String message, Track track, int duration,
			@CheckForNull Integer number, List<SlpParagraph> paragraphs) {
		String prefix = message == null ? "" : message + ", ";
		assertEquals(prefix + "duration mismatch", duration, track.getDuration());
		assertEquals(prefix + "number mismatch", number, track.getNumber());
		SlpParagraphTestUtil.assertSlpParagraphsEquals(prefix, paragraphs, track.getParagraphs());
	}

	private TrackTestUtil() {
		// prevent instantiation
	}
}
