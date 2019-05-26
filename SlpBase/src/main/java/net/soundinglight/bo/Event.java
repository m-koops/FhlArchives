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
 * An SLP event (e.g. retreat, workshop, etc)
 *
 */
@XmlRootElement(name = "event", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlType(propOrder = { "preamble", "conclusion", "sessions" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Entity {
	@XmlAttribute(name = "name", required = true)
	private String name;
	@XmlElementWrapper(name = "preamble", required = true)
	@XmlElement(name = "paragraph")
	private final List<SlpParagraph> preamble;
	@XmlElementWrapper(name = "conclusion", required = true)
	@XmlElement(name = "paragraph")
	private final List<SlpParagraph> conclusion;
	@XmlElement(name = "session", required = true)
	private final List<SlpSession> sessions = new ArrayList<>();

	/**
	 * C'tor.
	 *
	 * @param name the name of the event.
	 * @param preamble the preamble of the event.
	 * @param conclusion the conclusion of the event.
	 * @param sessions the sessions of this event.
	 */
	public Event(String name, List<SlpParagraph> preamble, List<SlpParagraph> conclusion, List<SlpSession> sessions) {
		this.name = name;
		this.preamble = preamble;
		this.conclusion = conclusion;
		this.sessions.addAll(sessions);
	}

	public String getName() {
		return name;
	}

	/**
	 * @return the preamble.
	 */
	public List<SlpParagraph> getPreamble() {
		return preamble;
	}

	/**
	 * @return the conclusion.
	 */
	public List<SlpParagraph> getConclusion() {
		return conclusion;
	}

	/**
	 * @return the sessions.
	 */
	public List<SlpSession> getSessions() {
		return sessions;
	}

	@Override
	public String toString() {
		if (sessions.isEmpty()) {
			return name + ": no sessions";
		}

		String first = sessions.get(0).getDetails().getId();
		String last = sessions.get(sessions.size() - 1).getDetails().getId();
		return name + ": sessions " + first + "-" + last;
	}

	@SuppressWarnings("unused")
	private Event() {
		// for JAXB
		this("", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
	}
}
