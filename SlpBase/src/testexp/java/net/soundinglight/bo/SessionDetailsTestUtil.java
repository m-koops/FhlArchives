/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import javax.annotation.CheckForNull;

import static net.soundinglight.AssertExt.assertBooleanEquals;
import static org.junit.Assert.assertEquals;

public final class SessionDetailsTestUtil {

	public static void assertSessionDetailsEquals(SessionDetails expected, SessionDetails actual) {
		assertSessionDetailsEquals(null, expected, actual);
	}

	public static void assertSessionDetailsEquals(@CheckForNull String message,
			SessionDetails expected, SessionDetails actual) {
		String prefix = message == null ? "" : message + ", ";
		assertEquals(prefix + "id mismatch", expected.getId(), actual.getId());
		assertBooleanEquals(prefix + "available mismatch", expected.isAvailable(), actual.isAvailable());
		if (actual.isAvailable()) {
			assertEquals(prefix + "title mismatch", expected.getTitle(), actual.getTitle());
			assertEquals(prefix + "subtitle mismatch", expected.getSubTitle(), actual.getSubTitle());
			assertEquals(prefix + "date mismatch", expected.getDate(), actual.getDate());
			assertEquals(prefix + "recommendation mismatch", expected.getRecommendation(),
					actual.getRecommendation());
			assertEquals(prefix + "duration mismatch", expected.getDuration(), actual.getDuration());
			assertEquals(prefix + "durationSideA mismatch", expected.getDurationSideA(),
					actual.getDurationSideA());
			assertEquals(prefix + "durationSideB mismatch", expected.getDurationSideB(),
					actual.getDurationSideB());
			assertEquals(prefix + "restriction mismatch", expected.getRestriction(),
					actual.getRestriction());
			assertEquals(prefix + "subrestriction mismatch", expected.getSubRestrictions(),
					actual.getSubRestrictions());
			assertEquals(prefix + "momentOfDay mismatch", expected.getMomentOfDay(),
					actual.getMomentOfDay());
			assertEquals(prefix + "keyTopics mismatch", expected.getKeyTopics(),
					actual.getKeyTopics());
			assertEquals(prefix + "additional remarks mismatch", expected.getAdditionalRemarks(),
					actual.getAdditionalRemarks());
		}
	}

	private SessionDetailsTestUtil() {
		// prevent instantiation
	}
}
