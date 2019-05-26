/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import net.soundinglight.bo.Event;
import net.soundinglight.bo.Image;
import net.soundinglight.bo.SlpSession;
import net.soundinglight.bo.TextFragment;
import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.MarshalUtil;
import net.soundinglight.parse.strategy.ParserStrategy2005;
import net.soundinglight.util.IOUtil;
import net.soundinglight.util.StringUtil;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SlpParserTest {
    @Test
    public void testShouldParseEventWithPreambleAndConclusion() throws Exception {
        Event event = parse("/net/soundinglight/sample.poi.xml", new ParserStrategy2005());
        List<SlpSession> sessions = event.getSessions();
        assertEquals(1, sessions.size());
        assertEquals("1", sessions.get(0).getDetails().getId());

        assertMarshalling("/net/soundinglight/sample.slp.xml", SlpParserTest.class, event, TextFragment.class, Image.class);
    }

    private void assertMarshalling(String resource, Class<?> resourceClz, Event instance, Class<?
            extends Entity>... classes) throws JAXBException, IOException {
        String actual = MarshalUtil.marshalToString(instance, classes);
        String expected = IOUtil.readResourceAsString(resourceClz, resource);
        assertEquals(expected, StringUtil.normalizeNewLine(actual));
    }

    @Test
    public void testShouldParseEventWithoutPreambleAndConclusion() throws Exception {
        Event event = parse("/net/soundinglight/sample.no-preamble.poi.xml", new ParserStrategy2005());
        List<SlpSession> sessions = event.getSessions();
        assertEquals(1, sessions.size());
        assertEquals("1", sessions.get(0).getDetails().getId());

        assertMarshalling("/net/soundinglight/sample.no-preamble.slp.xml", SlpParserTest.class, event,
                TextFragment.class, Image.class);
    }

    private Event parse(String poiResource, ParserStrategy2005 strategy) throws ParseException {
        FileParserSource source = new FileParserSource(SlpParserTest.class.getResourceAsStream(poiResource));
        MemoryParserTarget target = new MemoryParserTarget();
        new SlpParser(strategy).parse(source, target, 1982);
        return target.getEvent();
    }
}
