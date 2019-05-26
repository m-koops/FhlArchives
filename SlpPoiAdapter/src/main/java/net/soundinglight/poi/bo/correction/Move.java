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
 * A move correction for a POI document.
 */
@XmlRootElement(name = "move")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Move extends Correction {
	@XmlAttribute(required = true)
	private final int id;
	@XmlAttribute(required = true)
	private final int after;

	/**
	 * C'tor.
	 *
	 * @param after the paragraph id after which to add the provided paragraphs, or 0 to add
	 *            paragraphs at the start of the document.
	 * @param paragraphs the paragraphs to add.
	 */
	public Move(int id, int after) {
		this.id = id;
		this.after = after;
	}

	public int getId() {
		return id;
	}

	/**
	 * @return the after.
	 */
	public int getAfter() {
		return after;
	}

	@Override
	public List<Paragraph> apply(List<Paragraph> paragraphs) throws PoiInputException {
		List<Paragraph> result = new ArrayList<>(paragraphs);
		int index = findParagraph(result, id);
		int afterIndex;
		if (after == 0) {
			afterIndex = after;
		} else {
			afterIndex = findParagraph(result, after);
			afterIndex += index <= afterIndex ? 0 : 1;
		}

		result.add(afterIndex, result.remove(index));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "move paragraph " + getId() + " after " + getAfter();
	}

	@SuppressWarnings("unused")
	private Move() {
		// for JAXB
		this(0, 0);
	}
}
