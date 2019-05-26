/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.JAXBConstants;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Moment of day for session.
 * 
 */
@XmlRootElement(name = "momentOfDay", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlEnum
public enum MomentOfDay implements Entity {
	@XmlEnumValue(value = "1st AM")
	MORNING1("1st Morning Session"),
	@XmlEnumValue(value = "2nd AM")
	MORNING2("2nd Morning Session"),
	@XmlEnumValue(value = "Morning")
	MORNING("Morning Session"),
	@XmlEnumValue(value = "Afternoon")
	AFTERNOON("Afternoon Session"),
	@XmlEnumValue(value = "Evening")
	EVENING("Evening Session"),
	@XmlEnumValue(value = "Unknown")
	UNKNOWN("Unknown");

	private final String displayName;

	private MomentOfDay(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the displayName.
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Parse a {@link String} to {@link MomentOfDay}.
	 * 
	 * @param value the value to parse.
	 * @return the matching {@link MomentOfDay}, or {@link #UNKNOWN} if no match is found.
	 */
	public static MomentOfDay fromString(@CheckForNull String value) {
		for (MomentOfDay moment : MomentOfDay.values()) {
			if (moment.getDisplayName().equalsIgnoreCase(value)) {
				return moment;
			}
		}

		return MomentOfDay.UNKNOWN;
	}
}
