/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.input;

import net.soundinglight.HWPFDocumentTestUtil;
import net.soundinglight.bo.Event;
import net.soundinglight.bo.SlpSession;
import net.soundinglight.input.PoiInputAdapter;
import net.soundinglight.input.PoiInputException;
import net.soundinglight.parse.MemoryParserTarget;
import net.soundinglight.parse.ParseException;
import net.soundinglight.parse.SlpParser;
import net.soundinglight.parse.SlpParserSource;
import net.soundinglight.parse.strategy.ParserStrategy2005;
import net.soundinglight.poi.bo.DocumentCorrections;
import net.soundinglight.process.ProcessException;
import org.apache.poi.hwpf.HWPFDocument;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ParserProcessorInputTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testShouldParseDocumentIntoEvent() throws Exception {
        HWPFDocument hwpfDoc = HWPFDocumentTestUtil.readAsHWPFDocument("sample.doc");
        ParserProcessorInput input = new ParserProcessorInput(
                new PoiInputAdapter(hwpfDoc, "sample document", DocumentCorrections.NONE), new ParserStrategy2005(),
                1982);

        Event event = input.getEvent();
        List<SlpSession> sessions = event.getSessions();
        assertEquals(1, sessions.size());
        assertEquals("1", sessions.get(0).getDetails().getId());
    }

    @Test
    public void testShouldWrapParseException() throws Exception {
        SlpParserSource source = Mockito.mock(SlpParserSource.class);
        when(source.getDocument()).thenThrow(new PoiInputException("deliberate"));

        thrown.expect(ProcessException.class);
        thrown.expectMessage("failed to parse the document");
        try {
            MemoryParserTarget parserTarget = new MemoryParserTarget();
            new SlpParser(new ParserStrategy2005()).parse(source, parserTarget, 1982);
            parserTarget.getEvent();
        } catch (ParseException e) {
            throw new ProcessException("failed to parse the document", e);
        }
    }
}
