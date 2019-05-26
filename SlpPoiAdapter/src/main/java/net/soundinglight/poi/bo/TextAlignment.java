/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.poi.hwpf.usermodel.Paragraph;

/**
 * Text alignment.
 * 
 */
@XmlRootElement(name = "alignment")
@XmlEnum
public enum TextAlignment {
	@XmlEnumValue(value = "left")
	LEFT,
	@XmlEnumValue(value = "right")
	RIGHT,
	@XmlEnumValue(value = "center")
	CENTER,
	@XmlEnumValue(value = "justify")
	JUSTIFY;

	static final int POI_JUSTIFICATION_LEFT = 0;
	static final int POI_JUSTIFICATION_CENTER = 1;
	static final int POI_JUSTIFICATION_RIGHT = 2;
	static final int POI_JUSTIFICATION_JUSTIFY = 3;

	/**
	 * Determine {@link TextAlignment} by the justification of a {@link Paragraph}.
	 * 
	 * @param paragraph the {@link Paragraph} to determine the justification of.
	 * @return the matching {@link TextAlignment}.
	 */
	public static TextAlignment byJustificationOfParagraph(Paragraph paragraph) {
		return byJustification(paragraph.getJustification());
	}

	static TextAlignment byJustification(int justification) {
		switch (justification) {
		case POI_JUSTIFICATION_CENTER:
			return CENTER;
		case POI_JUSTIFICATION_RIGHT:
			return RIGHT;
		case POI_JUSTIFICATION_JUSTIFY:
			return JUSTIFY;
		case POI_JUSTIFICATION_LEFT:
		default:
			return LEFT;
		}
	}
}
