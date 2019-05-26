package net.soundinglight.process.input;

import net.soundinglight.bo.Event;
import net.soundinglight.process.ProcessException;

public class MemoryProcessorInput implements SlpProcessorInput {
	private final Event event;

	public MemoryProcessorInput(Event event) {
		this.event = event;
	}

	@Override
	public Event getEvent() throws ProcessException {
		return event;
	}
}
