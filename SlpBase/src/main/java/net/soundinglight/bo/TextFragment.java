/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.JAXBConstants;

import javax.xml.bind.annotation.*;
import java.util.regex.Pattern;

/**
 * A fragment of text of a given type.
 */
@XmlRootElement(name = "fragment", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlType(propOrder = { "type", "style", "text" })
@XmlAccessorType(XmlAccessType.FIELD)
public class TextFragment extends SlpParagraphElement {
	private static final Pattern WHITESPACE_ONLY_RUN_PATTERN = Pattern.compile("^\\s*$");
	private static final Pattern WSPACE_OR_PUNCT_ONLY_RUN_PATTERN = Pattern.compile("^[\\s\\p{Punct}]*$");

	@XmlAttribute(required = true)
	private final TextType type;
	@XmlAttribute(required = true)
	private final TextStyle style;
	@XmlValue
	private final String text;

	/**
	 * C'tor.
	 *
	 * @param text the text.
	 */
	public TextFragment(String text) {
		this(TextType.LATIN, TextStyle.NORMAL, text);
	}

	/**
	 * C'tor.
	 *
	 * @param type the type of text.
	 * @param style the style of text.
	 * @param text the text.
	 */
	public TextFragment(TextType type, TextStyle style, String text) {
		this.type = type;
		this.style = style;
		this.text = text;
	}

	/**
	 * @return the type.
	 */
	public TextType getType() {
		return type;
	}

	/**
	 * @return the style.
	 */
	public TextStyle getStyle() {
		return style;
	}

	/**
	 * @return the text.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return <code>true</code> when empty.
	 */
	public boolean isEmpty() {
		return text.isEmpty();
	}

	/**
	 * @return <code>true</code> when empty or no other then whitespace characters.
	 */
	public boolean isBlank() {
		return WHITESPACE_ONLY_RUN_PATTERN.matcher(text).matches();
	}

	/**
	 * @return <code>false</code> when empty or no other then whitespace/punctuation characters,
	 *         otherwise <code>true</code>.
	 */
	public boolean hasSignificantContent() {
		return !WSPACE_OR_PUNCT_ONLY_RUN_PATTERN.matcher(text).matches();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return text + " (" + type.getCssClass() + "/" + style.getCssClass() + ")";
	}

	/**
	 * Join two fragments to one.
	 *
	 * @param left the left fragment to join.
	 * @param right the right fragment to join.
	 * @return the joined fragment.
	 */
	public static TextFragment join(TextFragment left, TextFragment right) {
		String joined = left.getText() + right.getText();

		TextFragment characteristics;
		if (left.hasSignificantContent()) {
			characteristics = left;
		} else {
			characteristics = right.hasSignificantContent() ? right : left;
		}

		return new TextFragment(characteristics.getType(), characteristics.getStyle(), joined);
	}

	@SuppressWarnings("unused")
	private TextFragment() {
		// for JAXB
		this("");
	}
}
