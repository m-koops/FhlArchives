/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * Type to improve readability of code.
 */
@SuppressWarnings("serial")
public class Section extends ArrayList<Paragraph> {
	/**
	 * C'tor.
	 * 
	 * @param content the content of the section.
	 */
	public Section(List<Paragraph> content) {
		super(content);
	}
}
