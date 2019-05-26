/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static net.soundinglight.AssertExt.assertPrivateCtor;

import static org.junit.Assert.*;

import java.io.File;

import net.soundinglight.ScratchPath;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class IOTestUtilTest {
	private static ScratchPath scratchPath;

	@BeforeClass
	public static void setupClass() {
		scratchPath = new ScratchPath(IOUtilTest.class);
	}

	@AfterClass
	public static void teardownClass() {
		CloseUtil.safeClose(scratchPath);
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(IOTestUtil.class);
	}

	@Test
	public void testWriteStringToFileAndReadItBack() {
		String sample = "a small piece of text";
		File target = scratchPath.createAbstractFile("test.txt");

		IOTestUtil.writeStringToFile(sample, target);

		assertTrue(target.exists());
		assertTrue(target.isFile());
		assertTrue(target.length() > 0);

		assertEquals(sample, IOTestUtil.readFileAsString(target));
	}

}
