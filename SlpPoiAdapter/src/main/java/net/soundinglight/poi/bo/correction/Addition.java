/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo.correction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.util.StringUtil;

/**
 * A addition correction for a POI document.
 */
@XmlRootElement(name = "addition")
@XmlType(propOrder = { "additionalParagraphs" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Addition extends Correction {
	@XmlAttribute(required = true)
	private final int after;
	@XmlElement(name = "paragraph", required = true)
	private final List<Paragraph> additionalParagraphs = new ArrayList<>();

	/**
	 * C'tor.
	 *
	 * @param after the paragraph id after which to add the provided paragraphs, or 0 to add
	 *            paragraphs at the start of the document.
	 * @param additionalParagraphs the paragraphs to add.
	 */
	public Addition(int after, List<Paragraph> additionalParagraphs) {
		this.after = after;
		this.additionalParagraphs.addAll(additionalParagraphs);
	}

	/**
	 * @return the after.
	 */
	public int getAfter() {
		return after;
	}

	/**
	 * @return the paragraphs to add.
	 */
	public List<Paragraph> getAdditionalParagraphs() {
		return additionalParagraphs;
	}

	@Override
	public List<Paragraph> apply(List<Paragraph> paragraphs) throws PoiInputException {
		List<Paragraph> result = new ArrayList<>(paragraphs);
		int index = after == 0 ? after : findParagraph(result, after) + 1;
		result.addAll(index, additionalParagraphs);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "add paragraphs after " + getAfter() + ": " + StringUtil.asStringList(additionalParagraphs).toString();
	}

	@SuppressWarnings("unused")
	private Addition() {
		// for JAXB
		this(0, Collections.emptyList());
	}
}
