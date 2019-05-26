/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.core;

import static net.soundinglight.AssertExt.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class FatalProgrammingExceptionTest {
	public static class MyException extends Exception {
		private static final long serialVersionUID = 4440070157769913766L;
	}

	@Test
	public void testNoDefaultCtor() {
		assertNoDefaultCtor(FatalProgrammingException.class);
	}

	@Test
	public void testCtorWithJustAMessage() {
		FatalProgrammingException exception = new FatalProgrammingException("testMessage");

		assertContains("testMessage", exception.getMessage());
	}

	@Test
	public void testCtorWithMessageAndNestedException() {
		Exception nested = new MyException();
		FatalProgrammingException exception = new FatalProgrammingException("testMessage", nested);

		assertContains("testMessage", exception.getMessage());
		assertSame(nested, exception.getCause());
	}

}
