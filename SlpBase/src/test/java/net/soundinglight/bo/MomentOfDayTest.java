/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static net.soundinglight.AssertExt.assertEnumValues;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class MomentOfDayTest {
	private static final String SERIALIZED_RESOURCE = "../bo/serializedMomentOfDay.xml";

	@Test
	public void testShouldYieldMorning1() {
		assertMomentOfDay(MomentOfDay.MORNING1, "1st Morning Session");
	}

	@Test
	public void testShouldYieldMorning2() {
		assertMomentOfDay(MomentOfDay.MORNING2, "2nd Morning Session");
	}

	@Test
	public void testShouldYieldMorning() {
		assertMomentOfDay(MomentOfDay.MORNING, "Morning Session");
	}

	@Test
	public void testShouldYieldAfternoon() {
		assertMomentOfDay(MomentOfDay.AFTERNOON, "Afternoon Session");
	}

	@Test
	public void testShouldYieldEvening() {
		assertMomentOfDay(MomentOfDay.EVENING, "Evening Session");
	}

	@Test
	public void testShouldYieldUnknownOnNonParseableValue() {
		assertMomentOfDay(MomentOfDay.UNKNOWN, "This is not known");
	}

	@Test
	public void testEnumValues() throws Exception {
		assertEnumValues(MomentOfDay.class);
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, MomentOfDay.MORNING2);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		assertEquals(MomentOfDay.MORNING2,
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, MomentOfDay.class));
	}

	private void assertMomentOfDay(MomentOfDay expected, String value) {
		MomentOfDay moment = MomentOfDay.fromString(value);
		assertSame(expected, moment);
	}
}
