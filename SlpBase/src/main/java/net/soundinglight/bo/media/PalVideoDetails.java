/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo.media;

import net.soundinglight.jaxb.JAXBConstants;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Details of a PAL video media with recordings of a session.
 */
@XmlRootElement(name = "palVideoDetails", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
public class PalVideoDetails extends VideoDetails {

	/**
	 * C'tor.
	 * 
	 * @param id the media id.
	 * @param restriction the restriction that applies to the PAL media.
	 */
	public PalVideoDetails(String id, String restriction) {
		super(id, restriction);
	}

	@SuppressWarnings("unused")
	private PalVideoDetails() {
		// for JAXB
		this("", "");
	}
}
