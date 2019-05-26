/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.util.IOUtil;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class FileParserSourceTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testShouldUnmarshalFromFile() throws Exception {
		Document doc =
				new FileParserSource(IOUtil.getResourceAsFile(FileParserSource.class,
						"/net/soundinglight/sample.poi.xml")).getDocument();
		MarshalTestUtil.assertMarshalling("/net/soundinglight/sample.poi.xml", doc);
	}

	@Test
	@SuppressWarnings("boxing")
	public void testShouldWrapException() throws Exception {
		thrown.expect(PoiInputException.class);
		thrown.expectMessage("failed to unmarshal");

		InputStream is = Mockito.mock(InputStream.class);
		when(is.read()).thenThrow(new IOException("deliberate"));
		new FileParserSource(is).getDocument();
	}

}
