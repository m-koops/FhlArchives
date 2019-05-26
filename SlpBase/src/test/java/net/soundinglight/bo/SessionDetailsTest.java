/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.bo.media.NtscVideoDetails;
import net.soundinglight.bo.media.PalVideoDetails;
import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static net.soundinglight.AssertExt.assertBooleanEquals;
import static org.junit.Assert.assertEquals;

public class SessionDetailsTest {
    private static final List<String> KEY_TOPICS = Arrays.asList("Key topic 1", "Key topic 2",
            "Key topic 3");
    public static final SessionDetails SAMPLE_AVAILABLE = createAvailableSample();
    private static final SessionDetails SAMPLE_UNAVAILABLE = SessionDetails.builder("2").setAvailable(false).build();
    private static final String SERIALIZED_RESOURCE_AVAILABLE = "../bo/serializedSessionDetailsFull.xml";
    private static final String SERIALIZED_RESOURCE_UNAVAILABLE = "../bo/serializedSessionDetailsUnavailable.xml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGettersOfAvailailableSession() {
        assertEquals("1", SAMPLE_AVAILABLE.getId());
        assertBooleanEquals(true, SAMPLE_AVAILABLE.isAvailable());
        assertEquals("Session 1 title", SAMPLE_AVAILABLE.getTitle());
        assertEquals("sub title", SAMPLE_AVAILABLE.getSubTitle());
        assertEquals("04/22/1982", SAMPLE_AVAILABLE.getDate());
        assertEquals(Recommendation.LOW, SAMPLE_AVAILABLE.getRecommendation());
        assertEquals(53, SAMPLE_AVAILABLE.getDuration());
        assertEquals("Unrestricted", SAMPLE_AVAILABLE.getRestriction());
        assertEquals(Collections.singletonList("sub restriction"), SAMPLE_AVAILABLE.getSubRestrictions());
        assertEquals(MomentOfDay.MORNING1, SAMPLE_AVAILABLE.getMomentOfDay());
        assertEquals(KEY_TOPICS, SAMPLE_AVAILABLE.getKeyTopics());
    }

    @Test
    public void testGettersOfUnavailailableSession() {
        assertEquals("2", SAMPLE_UNAVAILABLE.getId());
        assertBooleanEquals(false, SAMPLE_UNAVAILABLE.isAvailable());
        assertEquals(null, SAMPLE_UNAVAILABLE.getTitle());
        assertEquals(null, SAMPLE_UNAVAILABLE.getSubTitle());
        assertEquals(null, SAMPLE_UNAVAILABLE.getDate());
        assertEquals(null, SAMPLE_UNAVAILABLE.getRecommendation());
        assertEquals(-1, SAMPLE_UNAVAILABLE.getDuration());
        assertEquals(null, SAMPLE_UNAVAILABLE.getRestriction());
        assertEquals(null, SAMPLE_UNAVAILABLE.getSubRestrictions());
        assertEquals(null, SAMPLE_UNAVAILABLE.getMomentOfDay());
        assertEquals(null, SAMPLE_UNAVAILABLE.getKeyTopics());
    }

    @Test
    public void testMarshallingAvailableDetails() throws Exception {
        MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE_AVAILABLE, SAMPLE_AVAILABLE);
    }

    @Test
    public void testMarshallingUnavailableDetails() throws Exception {
        MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE_UNAVAILABLE, SAMPLE_UNAVAILABLE);
    }

    @Test
    public void testUnmarshallingAvailableDetails() throws Exception {
        SessionDetailsTestUtil.assertSessionDetailsEquals(SAMPLE_AVAILABLE,
                MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE_AVAILABLE, SessionDetails.class));
    }

    @Test
    public void testUnmarshallingUnavailableDetails() throws Exception {
        SessionDetailsTestUtil.assertSessionDetailsEquals(SAMPLE_UNAVAILABLE,
                MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE_UNAVAILABLE, SessionDetails.class));
    }

    @Test
    public void testToStringShouldYieldDecentTextRepresentationForAnAvailableSession() {
        assertEquals("1: 04/22/1982; MORNING1; Session 1 title", SAMPLE_AVAILABLE.toString());
    }

    @Test
    public void testToStringShouldYieldDecentTextRepresentationForAnUnavailableSession() {
        assertEquals("2: is not available", SAMPLE_UNAVAILABLE.toString());
    }

    private static SessionDetails createAvailableSample() {
        return SessionDetails.builder("1")
                .setAvailable(true)
                .setTitle("Session 1 title")
                .setSubTitle("sub title")
                .setRestriction("Unrestricted")
                .setSubRestrictions(Collections.singletonList("sub restriction"))
                .setDate("04/22/1982")
                .setMomentOfDay(MomentOfDay.MORNING1)
                .setLocation("Amsterdam")
                .setDuration(53)
                .setRecommendation(Recommendation.LOW)
                .setKeyTopics(KEY_TOPICS)
                .setAdditionalRemarks(Collections.singletonList("some additional remark"))
                .setMediaDetails(Arrays.asList(new PalVideoDetails("123", "Restricted"),
                        new PalVideoDetails("456", "Semi-Restricted"), new NtscVideoDetails("789", "")))
                .setNotes(Collections.singletonList("A special note to session 1"))
                .build();
    }
}
