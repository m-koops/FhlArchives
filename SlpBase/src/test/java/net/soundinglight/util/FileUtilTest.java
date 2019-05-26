/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.soundinglight.AssertExt;

public class FileUtilTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testPrivateCtor() {
		AssertExt.assertPrivateCtor(FileUtil.class);
	}

	@Test
	public void testShouldFindExistingResourceWithRelativePath() {
		assertNotNull(FileUtil.findResource(FileUtil.class, "test.xml"));
	}

	@Test
	public void testShouldFindExistingResourceWithAbsolutePath() {
		assertNotNull(FileUtil.findResource(FileUtil.class, "/net/soundinglight/util/test.xml"));
	}

	@Test
	public void testShouldYieldNullWhenResourceCanNotBeFound() {
		assertNull(FileUtil.findResource(FileUtil.class, "unknown.xml"));
	}

	@Test
	public void testShouldGetExistingTestResource() {
		assertNotNull(FileUtil.getResource(FileUtil.class, "test.xml"));
	}

	@Test
	public void testShouldGetExistingMainResource() throws Exception {
		assertNotNull(FileUtil.getMainResource(FileUtil.class, "empty.txt", false));
	}

	@Test
	public void testShouldYieldNullForNonExistingMainResource() throws Exception {
		assertNull(FileUtil.getMainResource(FileUtil.class, "test.xml", false));
	}

	@Test
	public void testShouldCreateFileForNonExistingMainResource() throws Exception {
		File file = null;
		try {
			file = FileUtil.getMainResource(FileUtil.class, "new.txt", true);
			assertNotNull(file);
			assertTrue(file.exists());
			String path = FileUtil.getNormalizedPath(file);
			assertTrue(path.endsWith("SlpBase/src/main/resources/new.txt"));
		} catch (Throwable e) {
			if (file != null) {
				IOUtil.delete(file);
			}
		}
	}

	@Test
	public void testShouldThrowWhenResourceCanNotBeFound() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("missing resource unknown.xml in context of class " + "net.soundinglight.util.FileUtil");
		FileUtil.getResource(FileUtil.class, "unknown.xml");
	}

	@Test
	public void testShouldYieldNormalizedPath() {
		assertEquals("/", FileUtil.getNormalizedPath(new File("/")));
	}

	@Test
	public void testShouldYieldSourceFolder() {
		File resource = FileUtil.getResource(FileTestUtil.class, "test.xml");

		File srcRoot = FileUtil.getSrcRoot(resource);

		assertTrue(FileUtil.getNormalizedPath(srcRoot).endsWith("SlpBase/src"));
	}

	@Test
	public void testShouldYieldSourceFolderFromJarPath() throws Exception {
		File resource = FileUtil.getResource(FileTestUtil.class, "test.xml");
		File jarResource = new File(resource, "../../../../../../../libs/SlpBase-tests.jar!").getCanonicalFile();

		File srcRoot = FileUtil.getSrcRoot(jarResource);

		assertTrue(FileUtil.getNormalizedPath(srcRoot).endsWith("SlpBase/src"));
	}

	@Test
	public void testShouldThrowWhenSourceFolderCanNotBeFound() {
		thrown.expect(AssertionError.class);
		thrown.expectMessage("src root not found; started from /");

		FileUtil.getSrcRoot(new File("/"));
	}

	@Test
	public void testShouldYieldExpandedFullResourcePathForGivenRelativePath() {
		assertEquals("net/soundinglight/util/test.xml", FileUtil.getFullResourcePath(FileUtil.class, "test.xml"));
	}

	@Test
	public void testShouldYieldSamePathIfAskingForGivenAbsolutePath() {
		assertEquals("net/soundinglight/util/test.xml",
				FileUtil.getFullResourcePath(FileUtil.class, "/net/soundinglight/util/test.xml"));
	}
}
