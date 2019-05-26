/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

/**
 * Strategy for tapelist document "1777-1851,1994.doc".
 */
public class ParserStrategy1994Sessions1777To1851 extends ParserStrategy {
	/**
	 * C'tor.
	 */
	public ParserStrategy1994Sessions1777To1851() {
		super(SessionHeaderPatterns.create1994Session1777Patterns());
	}
}
