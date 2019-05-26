/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.output;

import net.soundinglight.bo.Event;
import net.soundinglight.core.FatalProgrammingException;
import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.util.IOUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class HtmlStringProcessorOutputTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testShouldOutputHtml() throws Exception {
        HtmlStringProcessorOutput output = new HtmlStringProcessorOutput();
        Event event = MarshalTestUtil.unmarshal("/net/soundinglight/sample.slp.xml", Event.class);
        output.setEvent(event);

        String expected = IOUtil.readResourceAsString(HtmlStringProcessorOutputTest.class,
                "/net/soundinglight/sample.slp.html");
        assertEquals(expected, output.getHtml());
    }

    @Test
    public void testShouldThrowWhenNoEventIsSetWhenGettingHtml() {
        thrown.expect(FatalProgrammingException.class);

        new HtmlStringProcessorOutput().getHtml();
    }
}
