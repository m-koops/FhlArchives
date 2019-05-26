/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi;

import net.soundinglight.HWPFDocumentTestUtil;
import net.soundinglight.input.PoiInputAdapter;
import net.soundinglight.input.PoiInputException;
import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.poi.bo.DocumentCorrections;

import org.apache.poi.hwpf.HWPFDocument;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MarshalSampleDocTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testShouldMarshalDocumentToXml() throws Exception {
		HWPFDocument hwpfDoc = HWPFDocumentTestUtil.readAsHWPFDocument("/net/soundinglight/sample.doc");
		Document doc = new PoiInputAdapter(hwpfDoc, "sample", DocumentCorrections.NONE).getDocument();
		MarshalTestUtil.assertMarshalling("/net/soundinglight/sample.poi.xml", doc);
	}

	@Test
	public void testShouldApplyCorrections() throws Exception {
		DocumentCorrections corrections =
				MarshalTestUtil.unmarshal("/net/soundinglight/sample.corrections.xml",
						DocumentCorrections.class);
		HWPFDocument hwpfDoc = HWPFDocumentTestUtil.readAsHWPFDocument("/net/soundinglight/sample.doc");
		Document doc = new PoiInputAdapter(hwpfDoc, "sample", corrections).getDocument();
		MarshalTestUtil.assertMarshalling("/net/soundinglight/sample.corrected.poi.xml", doc);
	}

	@Test
	public void testShouldThrowWhenUnableToApplyCorrections() throws Exception {
		DocumentCorrections corrections =
				MarshalTestUtil.unmarshal("/net/soundinglight/sample.falseCorrections.xml",
						DocumentCorrections.class);
		HWPFDocument hwpfDoc = HWPFDocumentTestUtil.readAsHWPFDocument("/net/soundinglight/sample.doc");

		thrown.expect(PoiInputException.class);
		thrown.expectMessage("failed to find paragraph with id '-1'");
		new PoiInputAdapter(hwpfDoc, "sample", corrections).getDocument();
	}
}
