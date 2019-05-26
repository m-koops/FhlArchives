/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo.media;

import net.soundinglight.jaxb.JAXBConstants;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Details of an NTSC video media with recordings of a session.
 */
@XmlRootElement(name = "ntscVideoDetails", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
public class NtscVideoDetails extends VideoDetails {

	/**
	 * C'tor.
	 * 
	 * @param id the media id.
	 * @param restriction the restriction that applies to the NTSC media.
	 */
	public NtscVideoDetails(String id, String restriction) {
		super(id, restriction);
	}

	@SuppressWarnings("unused")
	private NtscVideoDetails() {
		// for JAXB
		this("", "");
	}
}
