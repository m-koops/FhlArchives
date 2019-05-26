/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import static net.soundinglight.AssertExt.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertExtTest {
	private static class TestClassWithNoDefaultCtor {
		@SuppressWarnings("unused")
		public TestClassWithNoDefaultCtor(String param) {
			// nothing
		}
	}

	private static class TestClassWithDefaultCtor {
		@SuppressWarnings("unused")
		public TestClassWithDefaultCtor() {
			// nothing
		}
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(AssertExt.class);
	}

	@Test
	public void testNoDefaultCtor() {
		assertNoDefaultCtor(TestClassWithNoDefaultCtor.class);
	}

	@Test
	public void testNoDefaultCtorShouldThrowOnDefaultCtor() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("' is not expected to have a default constructor");

		assertNoDefaultCtor(TestClassWithDefaultCtor.class);
	}

	@Test
	public void assertEqualsShouldPassOnEqualBooleanValues() {
		assertBooleanEquals(false, false);
	}

	@Test
	public void assertEqualsShouldFailOnDifferentBooleanValues() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("extra message, expected:<true> but was:<false>");
		assertBooleanEquals("extra message", true, false);
	}

}
