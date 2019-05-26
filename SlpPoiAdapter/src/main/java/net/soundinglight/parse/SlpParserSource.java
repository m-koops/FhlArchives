/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.poi.bo.Document;

/**
 * Source for the SLP tapelist parser.
 * 
 */
public interface SlpParserSource {
	/**
	 * @return the raw tapelist document as a graph of SLP entities.
	 * @throws PoiInputException on failure.
	 */
	Document getDocument() throws PoiInputException;
}
