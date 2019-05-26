/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.JAXBConstants;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Text types.
 * 
 */
@XmlRootElement(name = "texttype", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlEnum
public enum TextType implements Entity {
	@XmlEnumValue(value = "latin")
	LATIN,
	@XmlEnumValue(value = "sacred")
	SACRED,
	@XmlEnumValue(value = "sacredCaps")
	SACRED_CAPS;

	/**
	 * @return the CSS class for the {@link TextType}.
	 */
	public String getCssClass() {
		return name().toLowerCase();
	}
}
