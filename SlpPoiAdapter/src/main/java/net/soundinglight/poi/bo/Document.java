/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.*;

import net.soundinglight.jaxb.Entity;
import net.soundinglight.util.StringUtil;

/**
 * A POI document.
 */
@XmlRootElement(name = "document")
@XmlType(propOrder = { "paragraphs" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Document implements Entity {
	@XmlElement(name = "paragraph", required = true)
	private List<Paragraph> paragraphs = new ArrayList<>();
	@XmlAttribute(name = "name", required = true)
	private String name;

	/**
	 * C'tor.
	 *
	 * @param name
	 * @param paragraphs the paragraphs of the document.
	 */
	public Document(String name, List<Paragraph> paragraphs) {
		this.name = name;
		this.paragraphs.addAll(paragraphs);
	}

	/**
	 * @return the paragraphs.
	 */
	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}

	/**
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name + "; " + StringUtil.asStringList(paragraphs).toString();
	}

	@SuppressWarnings("unused")
	private Document() {
		// for JAXB
	}
}
