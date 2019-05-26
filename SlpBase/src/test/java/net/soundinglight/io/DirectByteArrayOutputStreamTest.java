/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.io;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

public class DirectByteArrayOutputStreamTest {
	@Test
	public void testShouldYieldInputStreamWithCorrectBytes() throws Exception {
		byte[] expected = new byte[] { 0, 1, 2, 4, 5, 6 };

		@SuppressWarnings("resource")
		DirectByteArrayOutputStream dbaos = new DirectByteArrayOutputStream();
		dbaos.write(expected);

		InputStream is = dbaos.getInputStream();
		byte[] actual = new byte[expected.length];
		assertEquals(expected.length, is.read(actual));

		assertArrayEquals(expected, actual);
	}
}
