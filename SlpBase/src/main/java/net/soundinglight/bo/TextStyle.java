/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.core.UnreachableException;
import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.JAXBConstants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The text style.
 * 
 */
@XmlRootElement(name = "textstyle", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlEnum
public enum TextStyle implements Entity {
	@XmlEnumValue(value = "normal")
	NORMAL(false, false),
	@XmlEnumValue(value = "bold")
	BOLD(true, false),
	@XmlEnumValue(value = "italic")
	ITALIC(false, true),
	@XmlEnumValue(value = "bold-italic")
	BOLD_ITALIC(true, true);

	private boolean bold;
	private boolean italic;

	private TextStyle(boolean bold, boolean italic) {
		this.bold = bold;
		this.italic = italic;
	}

	/**
	 * @return whether the style is bold.
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * @return whether the style is italic.
	 */
	public boolean isItalic() {
		return italic;
	}

	/**
	 * Get the {@link TextStyle} based on values for bold and italic.
	 * 
	 * @param bold whether the style is bold or not.
	 * @param italic whether the style is italic or not.
	 * @return the matching {@link TextStyle}.
	 */
	public static TextStyle getStyle(boolean bold, boolean italic) {
		for (TextStyle style : values()) {
			if (style.isBold() == bold && style.isItalic() == italic) {
				return style;
			}
		}
		throw new UnreachableException(
				"all combinations of bold and italic are covered by the iteration");
	}

	/**
	 * @return the css class.
	 */
	public String getCssClass() {
		return name().toLowerCase();
	}
}
