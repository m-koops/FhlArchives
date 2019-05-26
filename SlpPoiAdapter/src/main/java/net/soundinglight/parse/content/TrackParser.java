/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.content;

import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.bo.Track;
import net.soundinglight.parse.strategy.ParserStrategy;
import net.soundinglight.util.StringUtil;

import javax.annotation.CheckForNull;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses {@link SlpParagraph}s to {@link Track}s.
 */
public class TrackParser {
    private static final Pattern TRACK_LABEL_PATTERN = Pattern.compile("^\\d+$");
    private ParserStrategy strategy;

    public TrackParser(ParserStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Parses {@link SlpParagraph}s to {@link Track}s.
     *
     * @param paragraphs the paragraphs to parse.
     * @return the resulting tracks.
     */
    public List<Track> parse(List<SlpParagraph> paragraphs) {
        if (paragraphs.isEmpty()) {
            return Collections.emptyList();
        }

        List<Track> tracks = new ArrayList<>();

        while (!paragraphs.isEmpty()) {
            tracks.add(extractNextTrack(paragraphs));
        }

        return tracks;
    }

    private Track extractNextTrack(List<SlpParagraph> paragraphs) {
        List<SlpParagraph> extracted = extractParagraphsForTrack(paragraphs);
        int duration = determineTrackDuration(extracted);
        Integer trackNumber = determineTrackNumber(extracted);

        return new Track(duration, trackNumber, stripBlankParagraphsFromEnd(extracted));
    }

    private List<SlpParagraph> extractParagraphsForTrack(List<SlpParagraph> paragraphs) {
        List<SlpParagraph> extracted = new ArrayList<>();

        Iterator<SlpParagraph> it = paragraphs.listIterator();
        extracted.add(it.next());
        it.remove();

        while (it.hasNext()) {
            SlpParagraph paragraph = it.next();
            String label = paragraph.getParagraphLabel();
            if (label != null && TRACK_LABEL_PATTERN.matcher(label).matches()) {
                break;
            }

            extracted.add(paragraph);
            it.remove();
        }

        return extracted;
    }

    private int determineTrackDuration(List<SlpParagraph> paragraphs) {
        return Integer.parseInt(extractTrackDurationString(paragraphs));
    }

    private String extractTrackDurationString(List<SlpParagraph> paragraphs) {
        Pattern trackDurationPattern = strategy.getTrackDurationPattern();

        ListIterator<SlpParagraph> it = paragraphs.listIterator(paragraphs.size());
        while (it.hasPrevious()) {
            SlpParagraph paragraph = it.previous();
            String text = paragraph.asPlainText();

            Matcher matcher = trackDurationPattern.matcher(text);
            if (matcher.matches()) {
                it.remove();
                String durationMin = matcher.group("durationMin");
                return durationMin != null ? durationMin : "0";
            }
        }

        return "0";
    }

    @CheckForNull
    private Integer determineTrackNumber(List<SlpParagraph> extracted) {
        String firstParagraphLabel = extracted.get(0).getParagraphLabel();

        if (firstParagraphLabel == null || "-".equals(firstParagraphLabel)) {
            return null;
        }

        return Integer.valueOf(firstParagraphLabel);
    }

    private List<SlpParagraph> stripBlankParagraphsFromEnd(List<SlpParagraph> paragraphs) {
        List<SlpParagraph> stripped = new ArrayList<>(paragraphs);

        ListIterator<SlpParagraph> it = stripped.listIterator(stripped.size());
        while (it.hasPrevious()) {
            if (!StringUtil.isNullOrEmpty(it.previous().asPlainText())) {
                break;
            }

            it.remove();
        }

        return stripped;
    }
}
