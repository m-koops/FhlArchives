/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.Entity;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Base class to both TextFragment and Image.
 *
 */
@XmlTransient
public abstract class SlpParagraphElement implements Entity {

	/**
	 * @return <code>true</code> when element is actually a {@link TextFragment}.
	 */
	public boolean isTextFragment() {
		return this instanceof TextFragment;
	}

	/**
	 * @return the element cast as a {@link TextFragment}.
	 */
	public TextFragment asTextFragment() {
		return (TextFragment)this;
	}

	/**
	 * @return <code>true</code> when element is actually an {@link Image}.
	 */
	public boolean isImage() {
		return this instanceof Image;
	}

	/**
	 * @return the element cast as an {@link Image}.
	 */
	public Image asImage() {
		return (Image)this;
	}
}
