/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.input;

import net.soundinglight.HWPFDocumentTestUtil;
import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.poi.bo.Document;
import org.apache.poi.hwpf.HWPFDocument;
import org.junit.Test;

public class HWPFDocumentConverterTest {
    @Test
    public void testShouldConvertDocIntoMarshallableDocumentLeavingNonUnicodeCharactersInPlace() throws Exception {
        HWPFDocument hwpfDoc = HWPFDocumentTestUtil.readAsHWPFDocument("/net/soundinglight/sample.doc");
        Document doc = new HWPFDocumentConverter("sample doc").convertDocument(hwpfDoc);

        MarshalTestUtil.assertMarshalling("/net/soundinglight/sample.poi.raw.nocharsreplaced.xml", doc);
    }
}
