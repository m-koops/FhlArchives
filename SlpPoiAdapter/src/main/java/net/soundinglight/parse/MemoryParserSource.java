/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import net.soundinglight.poi.bo.Document;

public class MemoryParserSource implements SlpParserSource {
	private final Document document;

	public MemoryParserSource(Document document) {
		this.document = document;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document getDocument() {
		return document;
	}
}
