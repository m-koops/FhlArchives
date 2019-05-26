package net.soundinglight.bo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Text alignment.
 *
 */
@XmlRootElement(name = "alignment")
@XmlEnum
public enum Alignment {
	@XmlEnumValue(value = "left") LEFT,
	@XmlEnumValue(value = "right") RIGHT,
	@XmlEnumValue(value = "center") CENTER,
	@XmlEnumValue(value = "justify") JUSTIFY
}
