/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import javax.xml.bind.annotation.XmlTransient;

import net.soundinglight.jaxb.Entity;

/**
 * Base class to both CharacterRun and Picture.
 *
 */
@XmlTransient
public abstract class ParagraphElement implements Entity {

	/**
	 * @return <code>true</code> when element is actually a {@link CharacterRun}.
	 */
	public boolean isCharacterRun() {
		return this instanceof CharacterRun;
	}

	/**
	 * @return the element cast as {@link CharacterRun}.
	 */
	public CharacterRun asCharacterRun() {
		return (CharacterRun)this;
	}

	/**
	 * @return <code>true</code> when element is actually a {@link Picture}.
	 */
	public boolean isPicture() {
		return this instanceof Picture;
	}

	/**
	 * @return the element cast as {@link Picture}.
	 */
	public Picture asPicture() {
		return (Picture)this;
	}
}
