/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;


/**
 * Strategy for tapelist documents written in 2004.
 */
public class ParserStrategy2004 extends ParserStrategy {

	/**
	 * C'tor.
	 */
	public ParserStrategy2004() {
		super(SessionHeaderPatterns.create2004Patterns());
	}
}
