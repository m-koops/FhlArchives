/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo.media;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Details of a PAL video media with recordings of a session.
 */
@XmlTransient
@XmlType(propOrder = { "id", "restriction" })
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class VideoDetails extends MediaDetails {
	@XmlAttribute
	@CheckForNull
	private final String id;
	@XmlAttribute
	@CheckForNull
	private final String restriction;

	/**
	 * C'tor.
	 * 
	 * @param id the media id.
	 * @param restriction the restriction that applies to the video media.
	 */
	public VideoDetails(String id, String restriction) {
		this.id = id;
		this.restriction = restriction;
	}

	/**
	 * @return the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the restriction.
	 */
	public String getRestriction() {
		return restriction;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return id + " (" + restriction + ")";
	}
}
