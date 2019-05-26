/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

/**
 * Strategy for tapelist documents written in 2002.
 */
public class ParserStrategy2002 extends ParserStrategy {
	/**
	 * C'tor.
	 * 
	 * @param keyTopicsMarker the key topics marker ("Key Topics" or "Keywords")
	 */
	public ParserStrategy2002(String keyTopicsMarker) {
		super(SessionHeaderPatterns.create2002Patterns("Tape", keyTopicsMarker));
	}
}
