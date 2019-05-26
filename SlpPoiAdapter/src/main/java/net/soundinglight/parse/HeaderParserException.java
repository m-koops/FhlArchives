/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */

package net.soundinglight.parse;

import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.poi.bo.Paragraph;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Exception to signal problems that occur during parsing of a session header.
 */
public class HeaderParserException extends ParseException {

    private final List<SlpParagraph> headerParagraphs;
    private final List<Pattern> patterns;

    /**
     * C'tor.
     *
     * @param message          the failure message.
     * @param headerParagraphs the paragraphs to parse.
     * @param patterns         the Regexp patterns applied to parse the header.
     */
    public HeaderParserException(String message, List<SlpParagraph> headerParagraphs, List<Pattern> patterns) {
        super(message);
        this.headerParagraphs = headerParagraphs;
        this.patterns = patterns;
    }

    /**
     * C'tor.
     *  @param message          the failure message.
     * @param headerParagraphs the paragraphs to parse.
     * @param patterns         the Regexp patterns applied to parse the header.
     * @param cause            the cause of the failure.
     */
    public HeaderParserException(String message, List<SlpParagraph> headerParagraphs, List<Pattern> patterns,
                                 Throwable cause) {
        super(message, cause);
        this.headerParagraphs = headerParagraphs;
        this.patterns = patterns;
    }

    /**
     * @return the paragraphs to parse.
     */
    public List<SlpParagraph> getHeaderParagraphs() {
        return headerParagraphs;
    }

    /**
     * @return the Regexp patterns applied to parse the header.
     */
    public List<Pattern> getPatterns() {
        return patterns;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("error: ").append(getMessage());

        if (getCause() != null) {
            stringBuilder.append("\ncause: ").append(getCause().getMessage());
        }

        stringBuilder.append("\n\nactual headerLines: ");
        for (SlpParagraph paragraph : headerParagraphs) {
            stringBuilder.append("\n\t")
                    .append("(")
                    .append(paragraph.getId())
                    .append(", ")
                    .append(paragraph.getAlignment())
                    .append(") ")
                    .append(paragraph.asPlainText().replace("\t", "\\t"));
        }

        stringBuilder.append("\n\navailable patterns:");
        for (Pattern pattern : patterns) {
            stringBuilder.append("\n\t");
            stringBuilder.append(pattern.pattern());
        }

        return stringBuilder.append("\n").toString();
    }
}
