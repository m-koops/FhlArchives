/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process;

import net.soundinglight.process.input.SlpProcessorInput;
import net.soundinglight.process.output.SlpProcessorOutput;

/**
 * Processes SLP tapelist documents.
 * 
 */
public class SlpProcessor {
	private final SlpProcessorInput input;
	private final SlpProcessorOutput output;

	/**
	 * C'tor.
	 * 
	 * @param input the processor input.
	 * @param output the processor output.
	 * 
	 */
	public SlpProcessor(SlpProcessorInput input, SlpProcessorOutput output) {
		this.input = input;
		this.output = output;
	}

	/**
	 * process the document.
	 * 
	 * @throws ProcessException on failure.
	 */
	public void process() throws ProcessException {
		output.setEvent(input.getEvent());
	}
}
