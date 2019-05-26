/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo.correction;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.poi.bo.Paragraph;

/**
 * A replacement correction for a POI document.
 */
@XmlRootElement(name = "replacement")
@XmlType(propOrder = { "replacementParagraph" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Replacement extends Correction {
	@XmlElement(name = "paragraph")
	private Paragraph replacementParagraph;

	/**
	 * C'tor.
	 *
	 * @param id the id of the paragraph on which to apply the replacement.
	 * @param replacementParagraph the replacement paragraph.
	 */
	public Replacement(Paragraph replacementParagraph) {
		this.replacementParagraph = replacementParagraph;
	}

	/**
	 * @return the replacementParagraph.
	 */
	public Paragraph getReplacementParagraph() {
		return replacementParagraph;
	}

	@Override
	public List<Paragraph> apply(List<Paragraph> paragraphs) throws PoiInputException {
		List<Paragraph> result = new ArrayList<>(paragraphs);
		int index = findParagraph(result, replacementParagraph.getId());

		result.remove(index);
		result.add(index, replacementParagraph);

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "replace paragraph " + replacementParagraph.getId() + " with: " + replacementParagraph.toString();
	}

	@SuppressWarnings("unused")
	private Replacement() {
		// for JAXB
		this(null);
	}
}
