/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.core;

import static net.soundinglight.AssertExt.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnreachableUtilTest {
	private static final String VALUE = "value";

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	private static class ClassWithUnreachableException {
		private final boolean shouldThrow;

		public ClassWithUnreachableException(boolean shouldThrow) {
			this.shouldThrow = shouldThrow;
		}

		public String getValue() throws IOException {
			if (shouldThrow) {
				throw new IOException("deliberate");
			}
			return VALUE;
		}
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(UnreachableUtil.class);
	}

	@Test
	public void testShouldYieldReturnValueFromCallableCallWhenNoException() {
		assertSame(VALUE, assertSuppressException(false));
	}

	@Test
	public void testShouldWrapThrownExceptionInUnreachableExceptionWhenThrown() {
		thrown.expect(UnreachableException.class);
		thrown.expectMessage("unexpected exception caught");

		assertSame(VALUE, assertSuppressException(true));
	}

	private String assertSuppressException(boolean shouldThrow) {
		final ClassWithUnreachableException nested = new ClassWithUnreachableException(shouldThrow);
		return UnreachableUtil.suppressException(new CallableT<String, IOException>() {
			@Override
			public String call() throws IOException {
				return nested.getValue();
			}
		});
	}
}
