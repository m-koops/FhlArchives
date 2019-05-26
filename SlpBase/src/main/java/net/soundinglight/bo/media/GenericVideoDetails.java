/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo.media;

import net.soundinglight.jaxb.JAXBConstants;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Details of an generic video media with recordings of a session.
 */
@XmlRootElement(name = "genericVideoDetails", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
public class GenericVideoDetails extends VideoDetails {

	/**
	 * C'tor.
	 * 
	 * @param id the media id.
	 * @param restriction the restriction that applies to the generic video.
	 */
	public GenericVideoDetails(String id, String restriction) {
		super(id, restriction);
	}

	@SuppressWarnings("unused")
	private GenericVideoDetails() {
		// for JAXB
		this("", "");
	}
}
