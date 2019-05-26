package net.soundinglight.process.input;

import static org.junit.Assert.assertSame;

import java.util.Collections;

import org.junit.Test;

import net.soundinglight.bo.Event;
import net.soundinglight.bo.SlpSession;
import net.soundinglight.bo.SlpParagraph;

public class MemoryProcessorInputTest {
	@Test
	public void shouldPassthroughEvent() throws Exception {
		Event event = new Event("", Collections.<SlpParagraph>emptyList(), Collections.<SlpParagraph>emptyList(),
				Collections.<SlpSession>emptyList());
		assertSame(event, new MemoryProcessorInput(event).getEvent());

	}

}
