/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

/**
 * Strategy for tapelist documents written from 1995 onwards.
 */
public class ParserStrategy1995 extends ParserStrategy {
	/**
	 * C'tor.
	 * 
	 * @param sessionMarker the session marker ("Session" or "Tape")
	 */
	public ParserStrategy1995(String sessionMarker) {
		super(SessionHeaderPatterns.create1995Patterns(sessionMarker));
	}
}
