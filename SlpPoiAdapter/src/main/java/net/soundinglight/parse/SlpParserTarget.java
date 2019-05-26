/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import net.soundinglight.bo.Event;

/**
 * Target for the SLP tapelist parser.
 * 
 */
public interface SlpParserTarget {
	/**
	 * Set the result of the parse operation.
	 * 
	 * @param event the result of the parse operation
	 */
	void setEvent(Event event);
}
