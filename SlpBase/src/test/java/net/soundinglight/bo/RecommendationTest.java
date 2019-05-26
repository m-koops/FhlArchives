/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.UnicodeConstants;
import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static net.soundinglight.AssertExt.assertEnumValues;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class RecommendationTest {
	private static final char SQR_ROOT = UnicodeConstants.SQR_ROOT;
	private static final String SERIALIZED_RESOURCE = "../bo/serializedRecommendation.xml";

	@Test
	public void testShouldYieldLow() {
		assertRecommendation(Recommendation.LOW, "" + SQR_ROOT);
	}

	@Test
	public void testShouldYieldMedium() {
		assertRecommendation(Recommendation.MEDIUM, "" + SQR_ROOT + SQR_ROOT);
	}

	@Test
	public void testShouldYieldHigh() {
		assertRecommendation(Recommendation.HIGH, "" + SQR_ROOT + SQR_ROOT + SQR_ROOT);
	}

	@Test
	public void testShouldYieldExcellent() {
		assertRecommendation(Recommendation.EXCELLENT, "" + SQR_ROOT + SQR_ROOT + SQR_ROOT + "+");
	}

	@Test
	public void testShouldYieldUnknownOnNonParseableValue() {
		assertRecommendation(Recommendation.UNKNOWN, "This is not known");
	}

	@Test
	public void testEnumValues() throws Exception {
		assertEnumValues(MomentOfDay.class);
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, Recommendation.MEDIUM);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		assertEquals(Recommendation.MEDIUM,
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, Recommendation.class));
	}

	private void assertRecommendation(Recommendation expected, String value) {
		Recommendation recommendation = Recommendation.fromString(value);
		assertSame(expected, recommendation);
	}
}
