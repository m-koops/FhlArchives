/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import static net.soundinglight.AssertExt.assertPrivateCtor;

import org.junit.Test;

public class SlpConstantsTest {
	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(SlpConstants.class);
	}
}
