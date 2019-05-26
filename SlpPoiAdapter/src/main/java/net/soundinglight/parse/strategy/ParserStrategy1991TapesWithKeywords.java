/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import java.util.regex.Pattern;

/**
 * Strategy for tapelist documents written in 1991 with tape id and keywords in session headers.
 */
public class ParserStrategy1991TapesWithKeywords extends ParserStrategy {

	/**
	 * C'tor.
	 */
	public ParserStrategy1991TapesWithKeywords() {
		super(SessionHeaderPatterns.create1991TapePatternsWithKeyWords());
	}

	@Override
	public Pattern getParagraphLabelPattern() {
		return ParserStrategy.PARAGRAPH_LABEL_NO_DASH_PATTERN;
	}
}
