/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.input;

import net.soundinglight.bo.Event;
import net.soundinglight.parse.MemoryParserTarget;
import net.soundinglight.parse.ParseException;
import net.soundinglight.parse.SlpParser;
import net.soundinglight.parse.SlpParserSource;
import net.soundinglight.parse.strategy.ParserStrategy;
import net.soundinglight.process.ProcessException;

/**
 * {@link SlpProcessorInput} that parses a document as input.
 */
public class ParserProcessorInput implements SlpProcessorInput {
	private final SlpParserSource parserSource;
	private final ParserStrategy strategy;
	private int tapelistYear;

	/**
	 * C'tor.
	 *
	 * @param parserSource the parser source.
	 * @param strategy the {@link ParserStrategy} to use.
	 * @param tapelistYear the year of the tapelist being parsed.
	 */
	public ParserProcessorInput(SlpParserSource parserSource, ParserStrategy strategy, int tapelistYear) {
		this.parserSource = parserSource;
		this.strategy = strategy;
		this.tapelistYear = tapelistYear;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Event getEvent() throws ProcessException {
		try {
			MemoryParserTarget parserTarget = new MemoryParserTarget();
			new SlpParser(strategy).parse(parserSource, parserTarget, tapelistYear);
			return parserTarget.getEvent();
		} catch (ParseException e) {
			throw new ProcessException("failed to parse the document", e);
		}
	}
}
