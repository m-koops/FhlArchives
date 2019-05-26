/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.UnicodeConstants;
import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.JAXBConstants;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Recoomendation for session.
 * 
 */
@XmlRootElement(name = "recommendation", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlEnum
public enum Recommendation implements Entity {
	@XmlEnumValue(value = "Low")
	LOW(createRawValue(1)),
	@XmlEnumValue(value = "Medium")
	MEDIUM(createRawValue(2)),
	@XmlEnumValue(value = "High")
	HIGH(createRawValue(3)),
	@XmlEnumValue(value = "Excellent")
	// CHECKSTYLE:OFF:MagicNumber
	EXCELLENT(createRawValue(3) + "+"),
	// CHECKSTYLE:ON:MagicNumber
	@XmlEnumValue(value = "Unknown")
	UNKNOWN("Unknown");

	private final String rawValue;

	private Recommendation(String rawValue) {
		this.rawValue = rawValue;
	}

	private static String createRawValue(int value) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < value; i++) {
			builder.append(UnicodeConstants.SQR_ROOT);
		}
		return builder.toString();
	}

	/**
	 * @return the rawValue.
	 */
	public String getRawValue() {
		return rawValue;
	}

	/**
	 * Parse a {@link String} to {@link Recommendation}.
	 * 
	 * @param value the value to parse.
	 * @return the matching {@link Recommendation}, or {@link #UNKNOWN} if no match is found.
	 */
	public static Recommendation fromString(@CheckForNull String value) {
		for (Recommendation recommendation : Recommendation.values()) {
			if (recommendation.getRawValue().equalsIgnoreCase(value)) {
				return recommendation;
			}
		}

		return Recommendation.UNKNOWN;
	}
}
