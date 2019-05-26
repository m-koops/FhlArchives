/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.output;

import net.soundinglight.ScratchPath;
import net.soundinglight.bo.Event;
import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.process.ProcessException;
import net.soundinglight.util.CloseUtil;
import net.soundinglight.util.IOTestUtil;
import net.soundinglight.util.IOUtil;
import net.soundinglight.util.StringUtil;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class XmlFileProcessorOutputTest {
    private static ScratchPath scratchPath;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setupClass() {
        scratchPath = new ScratchPath(XmlFileProcessorOutputTest.class);
    }

    @AfterClass
    public static void teardownClass() {
        CloseUtil.safeClose(scratchPath);
    }

    @Test
    public void testShouldMarshallToXmlFile() throws Exception {
        File outputFile = scratchPath.createAbstractFile("output.xml");

        Event event = MarshalTestUtil.unmarshal("/net/soundinglight/sample.slp.xml", Event.class);
        new XmlFileProcessorOutput(outputFile).setEvent(event);

        String expected = IOUtil.readResourceAsString(XmlFileProcessorOutputTest.class,
                "/net/soundinglight/sample.slp.xml");
        String actual = StringUtil.normalizeNewLine(IOTestUtil.readFileAsString(outputFile));
        assertEquals(expected, actual);
    }

    @Test
    public void testShouldWrapIoException() throws Exception {
        File outputFile = scratchPath.createAbstractFile("output.xml");
        outputFile.createNewFile();
        outputFile.setWritable(false);

        try {
            Event event = MarshalTestUtil.unmarshal("/net/soundinglight/sample.slp.xml", Event.class);

            thrown.expect(ProcessException.class);
            thrown.expectMessage("failed to write the event to file");
            new XmlFileProcessorOutput(outputFile).setEvent(event);
        } finally {
            outputFile.setWritable(true);
        }
    }
}
