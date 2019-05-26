/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo.correction;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.jaxb.Entity;
import net.soundinglight.poi.bo.Paragraph;

/**
 * A correction to the POI extraction needed to make SLP parser work.
 */
@XmlTransient
public abstract class Correction implements Entity {
	public abstract List<Paragraph> apply(List<Paragraph> paragraphs) throws PoiInputException;

	protected int findParagraph(List<Paragraph> paragraphs, int id) throws PoiInputException {
		for (int i = 0; i < paragraphs.size(); i++) {
			Paragraph paragraph = paragraphs.get(i);
			if (paragraph.getId() == id) {
				return i;
			}
		}

		throw new PoiInputException("failed to find paragraph with id '" + id + "'");
	}
}
