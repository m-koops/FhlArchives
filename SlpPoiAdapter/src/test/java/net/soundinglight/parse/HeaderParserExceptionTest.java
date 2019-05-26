/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import net.soundinglight.bo.SlpParagraph;
import net.soundinglight.bo.SlpParagraphTestUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class HeaderParserExceptionTest {
    @Test
    public void testMessageOnlyCtor() {
        String message = "message";
        List<SlpParagraph> paragraphs = Collections.singletonList(SlpParagraphTestUtil.createParagraph(1, "test"));
        List<Pattern> patterns = Collections.singletonList(Pattern.compile("^pattern$"));

        HeaderParserException exception = new HeaderParserException(message, paragraphs, patterns);

        assertSame(message, exception.getMessage());
        assertSame(paragraphs, exception.getHeaderParagraphs());
        assertSame(patterns, exception.getPatterns());
        assertEquals(
                "error: message\n\nactual headerLines: \n\t(1, LEFT) test\n\navailable patterns:\n\t^pattern$\n",
                exception.toString());
    }

    @Test
    public void testMessagePlusCauseCtor() {
        String message = "message";
        List<SlpParagraph> paragraphs = Collections.singletonList(SlpParagraphTestUtil.createParagraph(1, "test"));
        List<Pattern> patterns = Collections.singletonList(Pattern.compile("^pattern$"));
        IOException cause = new IOException("cause");

        HeaderParserException exception = new HeaderParserException(message, paragraphs, patterns, cause);

        assertSame(message, exception.getMessage());
        assertSame(paragraphs, exception.getHeaderParagraphs());
        assertSame(patterns, exception.getPatterns());
        assertSame(cause, exception.getCause());
        assertEquals(
                "error: message\ncause: cause\n\nactual headerLines: \n\t(1, LEFT) test\n\navailable " + "patterns" +
                        ":\n\t^pattern$\n",
                exception.toString());
    }
}
