/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.core;

import java.util.logging.Level;

import net.soundinglight.LogStore;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UnreachableExceptionTest {

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Rule
	public final LogStore store = new LogStore(Level.INFO);

	@Test
	public void testMessageOnlyCtor() {
		thrown.expect(UnreachableException.class);
		thrown.expectMessage("test message");

		store.expectMsg(Level.SEVERE, "programming error: test message");

		throw new UnreachableException("test message");
	}

	@Test
	public void testThrowableOnlyCtor() {
		thrown.expect(UnreachableException.class);
		thrown.expectMessage("unexpected exception caught");

		store.expectMsg(Level.SEVERE, "programming error: unexpected exception caught");

		throw new UnreachableException(new Exception("deliberate"));
	}

	@Test
	public void testCtorWithMessageAndNestedThrowable() {
		class MyException extends Exception {
			private static final long serialVersionUID = 940367259464543255L;
		}

		thrown.expect(UnreachableException.class);
		thrown.expectMessage("test message");

		store.expectMsg(Level.SEVERE, "programming error: test message");

		throw new UnreachableException("test message", new MyException());
	}
}
