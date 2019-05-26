/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.input;

import net.soundinglight.bo.Event;
import net.soundinglight.process.ProcessException;
import net.soundinglight.process.SlpProcessor;

/**
 * Input to the {@link SlpProcessor}.
 */
public interface SlpProcessorInput {

	/**
	 * @return the event.
	 * @throws ProcessException on failure.
	 */
	Event getEvent() throws ProcessException;
}
