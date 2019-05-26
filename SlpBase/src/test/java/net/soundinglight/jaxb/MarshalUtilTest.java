/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.jaxb;

import static net.soundinglight.AssertExt.assertPrivateCtor;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.w3c.dom.Document;

import net.soundinglight.ScratchPath;
import net.soundinglight.io.DirectByteArrayOutputStream;
import net.soundinglight.util.CloseUtil;
import net.soundinglight.util.IOTestUtil;
import net.soundinglight.util.IOUtil;
import net.soundinglight.util.XmlTestUtil;

public class MarshalUtilTest {
	@XmlRootElement
	private static class Top implements Entity {
		@XmlElement
		private Nested nested;

		public Top(Nested nested) {
			this.nested = nested;
		}

		@SuppressWarnings("unused")
		Top() {
			// default c'tor needed for JAXB
		}

		public Nested getNested() {
			return nested;
		}
	}

	@XmlRootElement
	private static class Nested implements Entity {
		@XmlAttribute
		private String name;

		public Nested(String name) {
			this.name = name;
		}

		@SuppressWarnings("unused")
		Nested() {
			// default c'tor needed for JAXB
		}

		public String getName() {
			return name;
		}
	}

	private static final String SERIALIZED = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
			+ "<top>\n    <nested name=\"abc\u221A\"/>\n</top>\n";
	private static ScratchPath scratchPath;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	public static void setupClass() {
		scratchPath = new ScratchPath(MarshalUtilTest.class);
	}

	@AfterClass
	public static void teardownClass() {
		CloseUtil.safeClose(scratchPath);
	}

	@Test
	public void testMarshallToStream() throws Exception {
		DirectByteArrayOutputStream dbaos = new DirectByteArrayOutputStream();
		MarshalUtil.marshalToStream(new Top(new Nested("abc\u221A")), dbaos);
		assertEquals(SERIALIZED, IOUtil.toUtf8String(dbaos));
	}

	@Test
	public void testMarshallToString() throws Exception {
		assertEquals(SERIALIZED, MarshalUtil.marshalToString(new Top(new Nested("abc\u221A"))));
	}

	@Test
	public void testUnmarshalFromString() throws Exception {
		Top top = MarshalUtil.<Top>unmarshalFromString(SERIALIZED, Top.class);
		assertEquals("abc\u221A", top.getNested().getName());
	}

	@Test
	public void testUnmarshalFromSingleByteStringShouldThrow() throws Exception {
		MarshalUtil.<Top>unmarshalFromString(SERIALIZED, Top.class);
	}

	@Test
	public void testMarshallToAndUnmarshalFromFile() throws Exception {
		File tempFile = scratchPath.createAbstractFile("test.xml");
		MarshalUtil.marshalToFile(new Top(new Nested("abc\u221A")), tempFile);

		assertEquals(SERIALIZED, IOTestUtil.readFileAsString(tempFile));

		Top top = MarshalUtil.<Top>unmarshalFromFile(tempFile, Top.class);
		assertEquals("abc\u221A", top.getNested().getName());
	}

	@Test
	public void testMarshalToFileShouldThrowWhenFileNotFound() throws Exception {
		thrown.expect(IOException.class);

		File tempFile = scratchPath.createAbstractFile("test");
		IOUtil.createDir(tempFile);
		MarshalUtil.marshalToFile(new Top(new Nested("abc\u221A")), tempFile);
	}

	@Test
	public void testUnmarshalFromFileShouldThrowWhenFileNotFound() throws Exception {
		thrown.expect(IOException.class);

		File tempFile = scratchPath.createAbstractFile("test");
		MarshalUtil.<Top>unmarshalFromFile(tempFile, Top.class);
	}

	@Test
	public void testMarshallToDocument() throws Exception {
		Document document = MarshalUtil.marshalToDocument(new Top(new Nested("abc\u221A")));
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<top>\n" + "   <nested name=\"abc\u221A\"/>\n</top>",
				XmlTestUtil.asString(document));
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(MarshalUtil.class);
	}
}
