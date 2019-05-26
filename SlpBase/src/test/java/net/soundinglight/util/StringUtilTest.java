/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static net.soundinglight.AssertExt.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

public class StringUtilTest {
	private static final String ABC = "abc";

	@Test
	public void testEmptyIfNullShouldPassNonNullValues() {
		assertSame(ABC, StringUtil.emptyIfNull(ABC));
	}

	@Test
	public void testEmptyIfNullShouldTurnNullIntoEmptyString() {
		assertEquals("", StringUtil.emptyIfNull(null));
	}

	@Test
	public void testIsNullOrEmptyYieldsTrueForNull() {
		assertTrue(StringUtil.isNullOrEmpty(null));
	}

	@Test
	public void testIsNullOrEmptyYieldsTrueForEmptyString() {
		assertTrue(StringUtil.isNullOrEmpty(""));
	}

	@Test
	public void testIsNullOrEmptyYieldsFalseForNonEmptyString() {
		assertFalse(StringUtil.isNullOrEmpty(ABC));
	}

	@Test
	public void testAsStringList() {
		assertEquals(Arrays.asList("red", "blue"),
				StringUtil.asStringList(Arrays.asList(new File("red"), new File("blue"))));
	}

	@Test
	public void testNormalizeNewLineShouldNormalizeToLinuxLineEndings() {
		assertEquals("abc\ndef", StringUtil.normalizeNewLine("abc\r\ndef"));
		assertEquals("abc\ndef", StringUtil.normalizeNewLine("abc\ndef"));
	}

	@Test
	public void testJoinShouldYieldEmptyStringOnEmptyList() {
		assertEquals("", StringUtil.join(Collections.<String>emptyList(), ";"));
	}

	@Test
	public void testJoinShouldYieldItemStringOnSingletonList() {
		assertEquals("a", StringUtil.join(Arrays.asList("a"), ";"));
	}

	@Test
	public void testJoinShouldYieldAllItemJoined() {
		assertEquals("a;b;;c", StringUtil.join(Arrays.asList("a", "b", null, "c"), ";"));
		assertEquals(";a;;b;c", StringUtil.join(Arrays.asList(null, "a", null, "b", "c"), ";"));
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(StringUtil.class);
	}
}
