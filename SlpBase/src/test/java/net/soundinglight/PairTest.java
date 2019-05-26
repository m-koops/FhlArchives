/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import static org.junit.Assert.*;

import org.junit.Test;

public class PairTest {
	private static final Pair<String, Integer> PAIR = new Pair<>("ABC", Integer.valueOf(123));

	@Test
	public void testGetters() {
		assertEquals("ABC", PAIR.getLeft());
		assertEquals(123, PAIR.getRight().intValue());
	}

	@Test
	public void testToString() {
		assertEquals("(ABC,123)", PAIR.toString());
	}
}
