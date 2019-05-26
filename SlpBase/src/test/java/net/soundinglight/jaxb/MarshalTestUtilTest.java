/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.jaxb;

import static net.soundinglight.AssertExt.*;

import net.soundinglight.jaxb.MarshalTestUtil;

import org.junit.Test;

public class MarshalTestUtilTest {
	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(MarshalTestUtil.class);
	}
}
