/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.content;

import net.soundinglight.bo.*;
import net.soundinglight.parse.strategy.ParserStrategy2005;
import net.soundinglight.util.CollectionUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TrackParserTest {
    private static final SlpParagraph PARAGRAPH_1 = new SlpParagraph(1,
            Collections.singletonList(new TextFragment("paragraph 1")), Alignment.LEFT, false, "1", null);
    private static final SlpParagraph PARAGRAPH_2 = new SlpParagraph(2,
            Collections.singletonList(new TextFragment("paragraph 2")), Alignment.CENTER, false, "2", null);
    private static final SlpParagraph PARAGRAPH_DASH = new SlpParagraph(3,
            Collections.singletonList(new TextFragment("paragraph with dash as label")), Alignment.JUSTIFY, false, "-",
            null);
    private static final SlpParagraph PARAGRAPH_NO_LABEL = new SlpParagraph(3,
            Collections.singletonList(new TextFragment("paragraph with no label")), Alignment.JUSTIFY, false, null,
            null);
    private static final SlpParagraph PARAGRAPH_EMPTY = new SlpParagraph(4, Collections.<TextFragment>emptyList(),
            Alignment.LEFT, false, null, null);
    private static final SlpParagraph PARAGRAPH_TRACK_DURATION_MINS = new SlpParagraph(5,
            Collections.singletonList(new TextFragment("4 mins. (40') with some extra text")), Alignment.RIGHT, true,
            null, null);
    private static final SlpParagraph PARAGRAPH_TRACK_DURATION_SECS = new SlpParagraph(5,
            Collections.singletonList(new TextFragment("24 secs. (40') with some extra text")), Alignment.RIGHT, true,
            null, null);

    @Test
    public void testShouldParseEmtyListProperly() {
        assertParseTracks(Collections.<SlpParagraph>emptyList());
    }

    private void assertParseTracks(List<SlpParagraph> paragraphs, Track... tracks) {
        TrackTestUtil.assertTracksEquals(Arrays.asList(tracks),
                new TrackParser(new ParserStrategy2005()).parse(paragraphs));
    }

    @Test
    public void testShouldParseOneParagraphToATrack() {
        assertParseTracks(CollectionUtil.asModifiableList(PARAGRAPH_1), createTrack(1, PARAGRAPH_1));
    }

    @Test
    public void testShouldParseTwoParagraphsToTracks() {
        Track track1 = createTrack(1, PARAGRAPH_1);
        Track track2 = createTrack(2, PARAGRAPH_2);
        assertParseTracks(CollectionUtil.asModifiableList(PARAGRAPH_1, PARAGRAPH_2), track1, track2);
    }

    @Test
    public void testShouldParseDashLabelAsContinuationOfCurrentTrack() {
        List<SlpParagraph> paragraphs = CollectionUtil.asModifiableList(PARAGRAPH_1, PARAGRAPH_DASH, PARAGRAPH_2);

        Track track1 = createTrack(1, PARAGRAPH_1, PARAGRAPH_DASH);
        Track track2 = createTrack(2, PARAGRAPH_2);
        assertParseTracks(paragraphs, track1, track2);
    }

    @Test
    public void testShouldNotUseDashAsTrackLabel() {
        List<SlpParagraph> paragraphs = CollectionUtil.asModifiableList(PARAGRAPH_DASH);
        SlpParagraph[] paragraphs1 = {PARAGRAPH_DASH};

        Track track1 = new Track(0, null, Arrays.asList(paragraphs1));
        assertParseTracks(paragraphs, track1);
    }

    @Test
    public void testShouldParseParagraphWithoutLabelAsContinuationOfCurrentTrack() {
        List<SlpParagraph> paragraphs = CollectionUtil.asModifiableList(PARAGRAPH_1, PARAGRAPH_NO_LABEL,
                PARAGRAPH_EMPTY, PARAGRAPH_2);

        Track track1 = createTrack(1, PARAGRAPH_1, PARAGRAPH_NO_LABEL);
        Track track2 = createTrack(2, PARAGRAPH_2);
        assertParseTracks(paragraphs, track1, track2);
    }

    @Test
    public void testShouldExtractTrackDurationMinutesFromTrack() {
        List<SlpParagraph> paragraphs = CollectionUtil.asModifiableList(PARAGRAPH_1, PARAGRAPH_DASH, PARAGRAPH_EMPTY,
                PARAGRAPH_TRACK_DURATION_MINS, PARAGRAPH_EMPTY, PARAGRAPH_2);

        Track track1 = createTrack(4, 1, PARAGRAPH_1, PARAGRAPH_DASH);
        Track track2 = createTrack(0, 2, PARAGRAPH_2);
        assertParseTracks(paragraphs, track1, track2);
    }

    @Test
    public void testShouldConsumeTrackDurationSecondsFromTrack() {
        List<SlpParagraph> paragraphs = CollectionUtil.asModifiableList(PARAGRAPH_1, PARAGRAPH_DASH, PARAGRAPH_EMPTY,
                PARAGRAPH_TRACK_DURATION_SECS, PARAGRAPH_EMPTY, PARAGRAPH_2);

        Track track1 = createTrack(0, 1, PARAGRAPH_1, PARAGRAPH_DASH);
        Track track2 = createTrack(0, 2, PARAGRAPH_2);
        assertParseTracks(paragraphs, track1, track2);
    }

    private Track createTrack(int number, SlpParagraph... paragraphs) {
        return createTrack(0, number, paragraphs);
    }

    private Track createTrack(int duration, int number, SlpParagraph... paragraphs) {
        return new Track(duration, number, Arrays.asList(paragraphs));
    }

}
