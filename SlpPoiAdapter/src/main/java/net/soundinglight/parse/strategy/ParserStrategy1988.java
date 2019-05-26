/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

/**
 * Strategy for tapelist documents written in 1988.
 */
public class ParserStrategy1988 extends ParserStrategy {
	/**
	 * C'tor.
	 */
	public ParserStrategy1988() {
		super(SessionHeaderPatterns.create1988Patterns());
	}
}
