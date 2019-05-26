/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.strategy;

import java.util.regex.Pattern;

/**
 * Strategy for tapelist documents written in 1991 with key topics in session headers.
 */
public class ParserStrategy1991SessionsWithKeyTopics extends ParserStrategy {

	/**
	 * C'tor.
	 */
	public ParserStrategy1991SessionsWithKeyTopics() {
		super(SessionHeaderPatterns.create1991SessionPatternsWithKeyTopics());
	}

	@Override
	public Pattern getParagraphLabelPattern() {
		return ParserStrategy.PARAGRAPH_LABEL_NO_DASH_PATTERN;
	}
}
