/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ProcessExceptionTest {
	@Test
	public void testMessageOnlyCtor() {
		String message = "message";
		ProcessException exception = new ProcessException(message);
		assertSame(message, exception.getMessage());
	}

	@Test
	public void testMessagePlusCauseCtor() {
		String message = "message";
		IOException cause = new IOException("cause");
		ProcessException exception = new ProcessException(message, cause);
		assertSame(message, exception.getMessage());
		assertSame(cause, exception.getCause());
	}
}
