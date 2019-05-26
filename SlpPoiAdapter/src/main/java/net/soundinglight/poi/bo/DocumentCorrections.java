/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import net.soundinglight.input.PoiInputException;
import net.soundinglight.jaxb.Entity;
import net.soundinglight.poi.bo.correction.Addition;
import net.soundinglight.poi.bo.correction.Correction;
import net.soundinglight.poi.bo.correction.Move;
import net.soundinglight.poi.bo.correction.Removal;
import net.soundinglight.poi.bo.correction.Replacement;
import net.soundinglight.util.StringUtil;

/**
 * Correction to be made to a POI extraction to make the SLP parser work.
 */
@XmlRootElement(name = "documentCorrections")
@XmlType(propOrder = { "corrections" })
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentCorrections implements Entity {
	public static final DocumentCorrections NONE = new DocumentCorrections();

	@XmlAttribute(required = true)
	private int lastAppliedParagraphId = 0;

	@XmlElementRefs({ @XmlElementRef(name = "replacement", type = Replacement.class, required = false),
			@XmlElementRef(name = "addition", type = Addition.class, required = false),
			@XmlElementRef(name = "removal", type = Removal.class, required = false),
			@XmlElementRef(name = "move", type = Move.class, required = false) })
	private final List<Correction> corrections = new ArrayList<>();

	/**
	 * C'tor for testability.
	 *
	 * @param lastAppliedParagraphId the last id applied for an added paragraph.
	 */
	public DocumentCorrections(int lastAppliedParagraphId) {
		this.lastAppliedParagraphId = lastAppliedParagraphId;
	}

	public void add(Correction correction) {
		if (NONE == this) {
			throw new UnsupportedOperationException("No changes allowed to singleton NONE");
		}
		corrections.add(correction);
	}

	public void remove(Correction correction) {
		corrections.remove(correction);
	}

	/**
	 * @return the corrections.
	 */
	public List<Correction> getCorrections() {
		return new ArrayList<>(corrections);
	}

	public int getNextParagraphId() {
		return --lastAppliedParagraphId;
	}

	/**
	 * Apply the corrections to the paragraphs.
	 *
	 * @param paragraphs the paragraphs to apply the corrections to.
	 * @return the corrected paragraphs.
	 * @throws PoiInputException on failure.
	 */
	public List<Paragraph> apply(List<Paragraph> paragraphs) throws PoiInputException {
		List<Paragraph> result = new ArrayList<>(paragraphs);
		for (Correction correction : corrections) {
			result = correction.apply(result);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return StringUtil.asStringList(corrections).toString();
	}

	private DocumentCorrections() {
		// for JAXB
	}
}
