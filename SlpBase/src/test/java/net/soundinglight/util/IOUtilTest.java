/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import net.soundinglight.ScratchPath;
import net.soundinglight.core.FatalProgrammingException;
import net.soundinglight.io.StringInputStream;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

import static net.soundinglight.AssertExt.assertPrivateCtor;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IOUtilTest {
	private static ScratchPath scratchPath;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

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
		assertPrivateCtor(IOUtil.class);
	}

	@Test
	public void testOpenStreamShouldPassNullUrl() {
		assertNull(IOUtil.openStream((URL) null));
	}

	@Test
	public void testShouldOpenStreamOnValidUrl() {
		InputStream is = IOUtil.openStream(IOUtil.class.getResource("test.xml"));
		try {
			assertNotNull(is);
		} finally {
			CloseUtil.safeClose(is);
		}
	}

	@Test
	public void testOpenStreamShouldYieldNullOnErrorOpeningStreamFromUrl() throws Exception {
		assertNull(IOUtil.openStream(new URL("file:/doesnotexist")));
	}

	@Test
	public void testOpenStreamShouldPassNullFile() {
		assertNull(IOUtil.openStream((File) null));
	}

	@Test
	public void testShouldOpenStreamOnValidFile() throws Exception {
		InputStream is = IOUtil.openStream(new File(IOUtil.class.getResource("test.xml").toURI()));
		try {
			assertNotNull(is);
		} finally {
			CloseUtil.safeClose(is);
		}
	}

	@Test
	public void testOpenStreamShouldYieldNullOnErrorOpeningStreamFromFile() throws Exception {
		assertNull(IOUtil.openStream(new File(new URL("file:/doesnotexist").toURI())));
	}
	@Test
	public void testCreateAndDeleteSubDir() throws IOException {
		File subsir = scratchPath.createAbstractFile("sub");
		try {
			IOUtil.createDir(subsir);
			assertTrue(subsir.exists() && subsir.isDirectory());
			File tempFile = File.createTempFile("tempfile", null, subsir);
			assertTrue(tempFile.exists() && tempFile.isFile());
		} finally {
			IOUtil.delete(subsir);
			assertFalse(subsir.exists());
		}
	}

	@Test
	public void testCreateDirNullSafe() {
		IOUtil.createDir(null);
	}

	@Test
	@SuppressWarnings("boxing")
	public void testDeleteThrowsOnFailure() throws IOException {
		thrown.expect(IOException.class);
		thrown.expectMessage("Failed to delete file:");

		File file = mock(File.class);
		when(file.isDirectory()).thenReturn(Boolean.FALSE);
		when(file.delete()).thenReturn(Boolean.FALSE);

		IOUtil.delete(file);
	}

	@Test
	public void testCopyEmptyStream() throws IOException {
		assertCopyStream("");
	}

	@Test
	public void testCopyNonEmptyStream() throws IOException {
		assertCopyStream("with content");
	}

	@Test
	public void testCopyResource() throws IOException {
		File source = IOUtil.getResourceAsFile(IOUtil.class, "test.xml");

		File subdir = scratchPath.createAbstractFile("subdir");
		File target = new File(subdir, "temp.doc");
		try {
			IOUtil.copyResource(IOUtil.class.getResource("test.xml"), target);
			assertTrue(target.exists() && target.isFile());
			assertEquals(source.length(), target.length());
		} finally {
			IOUtil.delete(subdir);
		}
	}

	@Test
	public void testNormalizePathShouldNormalizeBackslashToSlash() throws Exception {
		assertEquals("some/path", IOUtil.normalizePathSeparator("some\\path"));
	}

	@Test
	public void testNormalizePathShouldLeaveSlashesUntouched() throws Exception {
		assertEquals("some/path", IOUtil.normalizePathSeparator("some/path"));
	}

	@Test
	public void testGetResourceAsFileShouldYieldAFileReferringToTheResource() {
		File resource = IOUtil.getResourceAsFile(IOUtil.class, "test.xml");
		assertTrue(resource.isFile());
		String path = resource.getPath();
		assertTrue(IOUtil.normalizePathSeparator(path).endsWith("net/soundinglight/util/test.xml"));
	}

	@Test
	public void testFileUrlAsFileShouldHandleUrlEncodedUrls() throws Exception {
		URL url = new URL("file:///c:/Documents%20and%20Settings/test.txt");
		assertPathEquals("c:\\Documents and Settings\\test.txt",
				IOUtil.fileUrlAsFile(url).getPath());
	}

	private void assertPathEquals(String expected, String actual) {
		assertEquals(IOUtil.normalizePathSeparator(expected), IOUtil.normalizePathSeparator(actual));
	}

	@Test
	public void testFileUrlAsFileShouldHandleNonUrlEncodedUrls() throws Exception {
		URL url = new URL("file:///c:/Documents and Settings/test.txt");
		assertPathEquals("c:\\Documents and Settings\\test.txt",
				IOUtil.fileUrlAsFile(url).getPath());
	}

	@Test
	public void testFileUrlAsFileShouldThrowOnNonFileUrls() throws Exception {
		thrown.expect(FatalProgrammingException.class);
		thrown.expectMessage("method can only be used for file urls");

		IOUtil.fileUrlAsFile(new URL("http://google.com"));
	}

	@Test
	public void testShouldYieldUtf8String() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintWriter writer = new PrintWriter(baos);
		try {
			writer.println("abc");
			writer.println("123");
			writer.println("def");
		} finally {
			CloseUtil.safeClose(writer);
		}
		assertEquals("abc\n123\ndef\n", StringUtil.normalizeNewLine(IOUtil.toUtf8String(baos)));

	}

	@Test
	public void testReadResourceAsString() throws Exception {
		String actual = IOUtil.readResourceAsString(IOUtilTest.class, "test.xml");
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<root xmlns=\"urn:slp:tapelist:v1\" version=\"1.0\">\n"
				+ "   <child>content</child>\n</root>", actual);
	}

	@Test
	public void shouldWriteStringToStream() throws Exception {
		String content = "some Content";
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		IOUtil.writeStringToStream(os, content);

		assertEquals(content, IOUtil.toUtf8String(os));
	}

	@Test
	public void testReadResourceAsStringShouldThrowOnNonExistingResource() throws Exception {
		thrown.expect(IOException.class);
		thrown.expectMessage("resource 'doesnotexist.xml' does not exist");

		IOUtil.readResourceAsString(IOUtilTest.class, "doesnotexist.xml");
	}

	private void assertCopyStream(String streamContent) throws IOException {
		StringInputStream is = new StringInputStream(streamContent);
		ByteArrayOutputStream os = new ByteArrayOutputStream();

		long bytesCopied = IOUtil.copyStream(is, os);

		assertEquals(is.toString().length(), bytesCopied);
		assertEquals(streamContent, IOUtil.toUtf8String(os));
	}
}
