/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import java.util.regex.Pattern;

/**
 * Strategy for tapelist documents written in 1991 with keywords in session headers.
 */
public class ParserStrategy1991SessionsWithKeywords extends ParserStrategy {

	/**
	 * C'tor.
	 */
	public ParserStrategy1991SessionsWithKeywords() {
		super(SessionHeaderPatterns.create1991SessionPatternsWithKeyWords());
	}

	@Override
	public Pattern getParagraphLabelPattern() {
		return ParserStrategy.PARAGRAPH_LABEL_NO_DASH_PATTERN;
	}
}
