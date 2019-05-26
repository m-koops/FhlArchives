/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class EventTest {
    private static final List<SlpParagraph> PREAMBLE = Collections.singletonList(
            SlpParagraphTestUtil.createParagraph(-1, "preamble"));
    private static final List<SlpParagraph> CONCLUSION = Collections.singletonList(
            SlpParagraphTestUtil.createParagraph(-2, "conclusion"));
    private static final Event SAMPLE = new Event("test event", PREAMBLE, CONCLUSION,
            Collections.singletonList(SlpSessionTest.SAMPLE));
    private static final String SERIALIZED_RESOURCE = "../bo/serializedEvent.xml";

    @Test
    public void testGetters() {
        assertEquals("test event", SAMPLE.getName());
        assertParagraph("preamble", SAMPLE.getPreamble());
        assertParagraph("conclusion", SAMPLE.getConclusion());
        List<SlpSession> sessions = SAMPLE.getSessions();
        assertEquals(1, sessions.size());
        assertSame(SlpSessionTest.SAMPLE, sessions.get(0));
    }

    private void assertParagraph(String expected, List<SlpParagraph> paragraphs) {
        assertEquals(1, paragraphs.size());
        assertEquals(expected, paragraphs.get(0).asPlainText());
    }

    @Test
    public void testMarshalling() throws Exception {
        MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
    }

    @Test
    public void testUnmarshalling() throws Exception {
        assertEventEquals(SAMPLE, MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, Event.class));
    }

    @Test
    public void testToStringShouldYieldDecentTextRepresentation() {
        assertEquals("test event: sessions 1-1", SAMPLE.toString());
    }

    @Test
    public void testToStringForEmptyEventShouldYieldDecentTextRepresentation() {
        assertEquals("test event: no sessions",
                new Event("test event", Collections.<SlpParagraph>emptyList(), Collections.<SlpParagraph>emptyList(),
                        Collections.<SlpSession>emptyList()).toString());
    }

    private void assertEventEquals(Event expected, Event actual) {
        assertEquals(expected.getName(), actual.getName());
        SlpParagraphTestUtil.assertSlpParagraphsEquals("preamble", expected.getPreamble(), actual.getPreamble());
        SlpParagraphTestUtil.assertSlpParagraphsEquals("conclusion", expected.getConclusion(), actual.getConclusion());
        SessionTestUtil.assertSessionsEquals(expected.getSessions(), actual.getSessions());
    }
}
