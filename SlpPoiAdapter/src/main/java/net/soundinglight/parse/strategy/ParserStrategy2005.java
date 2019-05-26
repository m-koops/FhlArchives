/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

/**
 * Strategy for tapelist documents written in 2005.
 */
public final class ParserStrategy2005 extends ParserStrategy {
	/**
	 * C'tor.
	 */
	public ParserStrategy2005() {
		super(SessionHeaderPatterns.create2005Patterns());
	}
}
