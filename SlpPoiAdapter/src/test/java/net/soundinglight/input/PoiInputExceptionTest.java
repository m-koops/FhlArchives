/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.input;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class PoiInputExceptionTest {
	@Test
	public void testMessageOnlyCtor() {
		String message = "message";
		PoiInputException exception = new PoiInputException(message);
		assertSame(message, exception.getMessage());
	}

	@Test
	public void testMessagePlusCauseCtor() {
		String message = "message";
		IOException cause = new IOException("cause");
		PoiInputException exception = new PoiInputException(message, cause);
		assertSame(message, exception.getMessage());
		assertSame(cause, exception.getCause());
	}
}
