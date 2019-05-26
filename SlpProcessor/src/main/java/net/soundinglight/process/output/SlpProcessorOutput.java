/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.output;

import net.soundinglight.bo.Event;
import net.soundinglight.process.ProcessException;
import net.soundinglight.process.SlpProcessor;

/**
 * Output for the {@link SlpProcessor}.
 */
public interface SlpProcessorOutput {

	/**
	 * Set the event.
	 * 
	 * @param event the event.
	 * @throws ProcessException on failure.
	 */
	void setEvent(Event event) throws ProcessException;
}
