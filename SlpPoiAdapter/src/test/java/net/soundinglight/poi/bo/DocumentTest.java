/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import net.soundinglight.jaxb.MarshalTestUtil;

public class DocumentTest {
	private static final String SERIALIZED_DOCUMENT_RESOURCE = "../poi/bo/serializedDocument.xml";
	private static final Document SAMPLE = DocumentTestUtil.createTestDocument();

	@Test
	public void testGetters() {
		assertEquals(DocumentTestUtil.NAME, SAMPLE.getName());
		assertEquals(Arrays.asList(DocumentTestUtil.PARAGRAPH1, DocumentTestUtil.PARAGRAPH2), SAMPLE.getParagraphs());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_DOCUMENT_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		Document instance = MarshalTestUtil.unmarshal(SERIALIZED_DOCUMENT_RESOURCE, Document.class);
		DocumentTestUtil.assertDocumentEquals(SAMPLE, instance);
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("test document; [[run1, mime: image/jpeg; hor. scale fact: 0.123; "
				+ "vert. scale factor: 1.0; DxaGoalMm: 30; DyaGoalMm: 40; "
				+ "base64Content: 'c2FtcGxlIG...', run2], [italic, bold]]", SAMPLE.toString());
	}
}
