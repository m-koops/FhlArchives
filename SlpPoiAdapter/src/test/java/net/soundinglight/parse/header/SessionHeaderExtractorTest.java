/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.header;

import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.jaxb.MarshalUtil;
import net.soundinglight.parse.content.ParagraphConverter;
import net.soundinglight.parse.strategy.ParserStrategy2005;
import net.soundinglight.poi.bo.Document;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SessionHeaderExtractorTest {

    @Test
    public void testShouldExtractHeaderIncludingTitle() throws Exception {
        Document document = MarshalUtil.unmarshalFromStream(SessionHeaderExtractorTest.class.getResourceAsStream(
                "/net/soundinglight/sample.no-preamble.poi.xml"), Document.class);
        ParagraphConverter paragraphConverter = new ParagraphConverter(new ParserStrategy2005());
        List<SlpParagraph> paragraphs = paragraphConverter.convert(document.getParagraphs(),
                ParagraphConverter.Mode.SESSION);

        List<SlpParagraph> header = new SessionHeaderExtractor().extractHeader(paragraphs);

        assertEquals(10, header.size());
        assertEquals("Session 1	Unrestricted\t22nd April 1982", header.get(0).asPlainText());
        assertEquals("LOREM IPSUM DOLOR SIT AMIT", header.get(8).asPlainText());
    }
}
