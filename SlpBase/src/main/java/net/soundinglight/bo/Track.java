/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.JAXBConstants;
import net.soundinglight.util.StringUtil;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A session track.
 *
 */
@XmlRootElement(name = "track", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlType(propOrder = { "duration", "number", "paragraphs" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Track implements Entity {
	@XmlAttribute(required = true)
	private final int duration;
	@XmlAttribute
	@CheckForNull
	private final Integer number;
	@XmlElement(name = "paragraph", required = true)
	private final List<SlpParagraph> paragraphs;

	/**
	 * C'tor.
	 *
	 * @param duration the duration in minutes of the track.
	 * @param number the track number of this track.
	 * @param paragraphs the {@link SlpParagraph}s of this track.
	 */
	public Track(int duration, @CheckForNull Integer number, List<SlpParagraph> paragraphs) {
		this.duration = duration;
		this.number = number;
		this.paragraphs = paragraphs;
	}

	/**
	 * @return the paragraphs.
	 */
	public List<SlpParagraph> getParagraphs() {
		return paragraphs;
	}

	/**
	 * @return the timeMins.
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @return the number.
	 */
	@CheckForNull
	public Integer getNumber() {
		return number;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return duration + "; " + (number == null ? "<no number>" : number) + "; " + StringUtil.asStringList(paragraphs);
	}

	@SuppressWarnings("unused")
	private Track() {
		// for JAXB
		this(0, null, new ArrayList<SlpParagraph>());
	}

}
