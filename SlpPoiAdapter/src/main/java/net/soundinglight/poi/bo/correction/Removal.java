/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo.correction;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.poi.bo.Paragraph;

/**
 * A removal correction for a POI document.
 */
@XmlRootElement(name = "removal")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Removal extends Correction {
	@XmlAttribute(required = true)
	private final int from;
	@XmlAttribute(required = true)
	private final int to;

	/**
	 * C'tor.
	 *
	 * @param from the paragraph id from which to apply the removal.
	 * @param to the paragraph id till which to apply the removal.
	 */
	public Removal(int from, int to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * @return the from.
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @return the to.
	 */
	public int getTo() {
		return to;
	}

	@Override
	public List<Paragraph> apply(List<Paragraph> paragraphs) throws PoiInputException {
		List<Paragraph> result = new ArrayList<>(paragraphs);
		int fromIndex = findParagraph(result, from);
		int toIndex = findParagraph(result, to);
		result.subList(fromIndex, toIndex + 1).clear();
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "remove paragraphs " + getFrom() + "-" + getTo();
	}

	@SuppressWarnings("unused")
	private Removal() {
		// for JAXB
		this(0, 0);
	}
}
