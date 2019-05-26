/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.JAXBConstants;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An SLP session (e.g. 1 session of a retreat, workshop, etc)
 *
 */
@XmlRootElement(name = "session", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlType(propOrder = { "details", "tracks", "trackTimings" })
@XmlAccessorType(XmlAccessType.FIELD)
public class SlpSession implements Entity {
	@XmlElement(required = true)
	private final SessionDetails details;
	@XmlElementWrapper(required = true)
	@XmlElement(name = "track")
	private final List<Track> tracks;
	@XmlElementWrapper(required = true)
	@XmlElement(name = "trackTiming")
	private final List<String> trackTimings;

	/**
	 * C'tor.
	 *
	 * @param details the session details.
	 * @param tracks the tracks.
	 * @param trackTimings the track timings.
	 */
	public SlpSession(SessionDetails details, List<Track> tracks, List<String> trackTimings) {
		this.details = details;
		this.tracks = tracks;
		this.trackTimings = trackTimings;
	}

	/**
	 * @return the details.
	 */
	public SessionDetails getDetails() {
		return details;
	}

	/**
	 * @return the tracks.
	 */
	public List<Track> getTracks() {
		return tracks;
	}

	/**
	 * @return the trackTimings.
	 */
	public List<String> getTrackTimings() {
		return trackTimings;
	}

	@Override
	public String toString() {
		return details.toString() + "; " + tracks.size() + " tracks; " + trackTimings.size() + " timings";
	}

	@SuppressWarnings("unused")
	private SlpSession() {
		// for JAXB
		this(null, new ArrayList<Track>(), new ArrayList<String>());
	}

}
