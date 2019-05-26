/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import java.util.regex.Pattern;

/**
 * Strategy for tapelist documents written in 1991 for which no key topics are defined in the
 * session headers.
 */
public class ParserStrategy1991SessionsWithNoKeyTopics extends ParserStrategy {

	/**
	 * C'tor.
	 */
	public ParserStrategy1991SessionsWithNoKeyTopics() {
		super(SessionHeaderPatterns.create1991SessionPatternsWithNoKeyTopics());
	}

	@Override
	public Pattern getParagraphLabelPattern() {
		return ParserStrategy.PARAGRAPH_LABEL_NO_DASH_PATTERN;
	}
}
