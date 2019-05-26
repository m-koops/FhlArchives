/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import javax.annotation.CheckForNull;

import net.soundinglight.bo.Event;

/**
 * {@link SlpParserTarget} that holds reference to the parse result.
 * 
 */
public class MemoryParserTarget implements SlpParserTarget {
	@CheckForNull
	private Event event = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return the event.
	 */
	public Event getEvent() {
		assert event != null : "first run the parser";
		return event;
	}
}
