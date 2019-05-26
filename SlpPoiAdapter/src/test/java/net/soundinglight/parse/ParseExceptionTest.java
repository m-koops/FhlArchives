/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ParseExceptionTest {
	@Test
	public void testMessageOnlyCtor() {
		String message = "message";
		ParseException exception = new ParseException(message);
		assertSame(message, exception.getMessage());
	}

	@Test
	public void testMessagePlusCauseCtor() {
		String message = "message";
		IOException cause = new IOException("cause");
		ParseException exception = new ParseException(message, cause);
		assertSame(message, exception.getMessage());
		assertSame(cause, exception.getCause());
	}
}
