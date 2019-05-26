/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.input;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.soundinglight.ScratchPath;
import net.soundinglight.bo.Event;
import net.soundinglight.bo.SlpSession;
import net.soundinglight.process.ProcessException;
import net.soundinglight.util.CloseUtil;

public class XmlFileProcessorInputTest {
	private static ScratchPath scratchPath;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@BeforeClass
	public static void setupClass() {
		scratchPath = new ScratchPath(XmlFileProcessorInputTest.class);
	}

	@AfterClass
	public static void teardownClass() {
		CloseUtil.safeClose(scratchPath);
	}

	@Test
	public void testShouldLoadEventFromFile() throws Exception {
		XmlFileProcessorInput input = new XmlFileProcessorInput(
				XmlFileProcessorInputTest.class.getResourceAsStream("/net/soundinglight/sample.slp.xml"));

		Event event = input.getEvent();
		List<SlpSession> sessions = event.getSessions();
		assertEquals(1, sessions.size());
		assertEquals("1", sessions.get(0).getDetails().getId());
	}

	@SuppressWarnings("unused")
	@Test
	public void testShouldThrowOnFileNotFound() throws Exception {
		thrown.expect(FileNotFoundException.class);
		File tempFile = scratchPath.createAbstractFile("doesNotExist");
		new XmlFileProcessorInput(tempFile);
	}

	@Test
	public void testShouldWrapJAXBException() throws Exception {
		thrown.expect(ProcessException.class);
		thrown.expectMessage("failed to load the event from file");
		File tempFile = scratchPath.createAbstractFile("empty");
		tempFile.createNewFile();
		new XmlFileProcessorInput(tempFile).getEvent();
	}

}
