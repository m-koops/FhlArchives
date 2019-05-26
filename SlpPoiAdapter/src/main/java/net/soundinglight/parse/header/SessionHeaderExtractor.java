/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.header;

import net.soundinglight.bo.Alignment;
import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.util.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Extracts the header lines from a tape list section.
 */
public class SessionHeaderExtractor {
    private static final Pattern FOLLOW_UP_SESSION_PATTERN = Pattern.compile("^(?:Session|Tape) \\d+ \\([^1]\\).*$");

    /**
     * Extract the session header from the sessions paragraphs.
     *
     * @param paragraphs the session paragraphs, to extract the session header from.
     * @return the extracted session header.
     */
    public List<SlpParagraph> extractHeader(List<SlpParagraph> paragraphs) {
        if (!paragraphs.isEmpty() && FOLLOW_UP_SESSION_PATTERN.matcher(paragraphs.get(0).asPlainText()).matches()) {
            return extractFollowUpHeader(paragraphs);
        }

        List<SlpParagraph> headerParagraphs = new ArrayList<>();

        Iterator<SlpParagraph> it = paragraphs.iterator();
        boolean hitTitle = false;
        boolean postTitle = false;
        while (it.hasNext()) {
            SlpParagraph paragraph = it.next();

            boolean isEmptyParagraph = paragraph.isEmpty();
            if (!isEmptyParagraph && paragraph.getAlignment() == Alignment.CENTER) {
                // The title is typically the first centered paragraph of the section
                hitTitle = true;
            }

            if (hitTitle && isEmptyParagraph) {
                // The title is followed by 1 or more empty line(s)
                postTitle = true;
            }

            if (postTitle) {
                if (!paragraph.isEmpty()) {
                    String paragraphLabel = paragraph.getParagraphLabel();
                    if (!StringUtil.isNullOrEmpty(paragraphLabel)) {
                        // The first labeled paragraph signals for the end of the session header
                        break;
                    }


                    //Fallback for sessions without paragraph labels
                    Alignment alignment = paragraph.getAlignment();
                    if (alignment == Alignment.JUSTIFY || alignment == Alignment.LEFT) {
                        String text = paragraph.asPlainText();

                        if (!text.startsWith("Notes") && !text.startsWith("Reference") && !text.startsWith(
                                "Additional remarks")) {
                            break;
                        }
                    }
                }
            }

            headerParagraphs.add(paragraph);
            it.remove();
        }

        return headerParagraphs;
    }

    private List<SlpParagraph> extractFollowUpHeader(List<SlpParagraph> paragraphs) {
        List<SlpParagraph> headerParagraphs = new ArrayList<>();

        Iterator<SlpParagraph> it = paragraphs.iterator();
        while (it.hasNext()) {
            SlpParagraph paragraph = it.next();

            headerParagraphs.add(paragraph);
            it.remove();

            if (paragraph.asPlainText().trim().startsWith("Side B")) {
                break;
            }
        }

        return headerParagraphs;
    }
}
