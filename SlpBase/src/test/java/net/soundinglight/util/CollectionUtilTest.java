/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static net.soundinglight.AssertExt.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class CollectionUtilTest {
	@Test
	public void testShouldYieldModifiableList() {
		String item1 = "1";
		String item2 = "2";
		List<String> list = CollectionUtil.asModifiableList(item1, item2);
		assertEquals(2, list.size());
		assertEquals(item1, list.remove(0));
		assertEquals(item2, list.get(0));
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(CollectionUtil.class);
	}

}
