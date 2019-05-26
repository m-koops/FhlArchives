/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.content;

import net.soundinglight.bo.SlpParagraphElement;
import net.soundinglight.bo.TextFragment;
import net.soundinglight.bo.TextStyle;
import net.soundinglight.bo.TextType;
import net.soundinglight.poi.bo.CharacterRun;
import net.soundinglight.poi.bo.Font;
import net.soundinglight.poi.bo.ParagraphElement;
import net.soundinglight.poi.bo.Picture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * {@link ParagraphElement}-to-{@link SlpParagraphElement} converter.
 */
public final class ParagraphElementConverter {
	private static final Pattern ONLY_ASCII_CHARS_PATTERN = Pattern.compile("^\\w*$");
	private static final Pattern ENDS_WITH_CR_PATTERN = Pattern.compile("\r$");

	/**
	 * Converts a collection of {@link ParagraphElement}s into a collection of
	 * {@link SlpParagraphElement}s.
	 * 
	 * @param elements the {@link ParagraphElement}s to convert.
	 * @return the resulting {@link SlpParagraphElement}s.
	 */
	public static List<SlpParagraphElement> convert(Collection<? extends ParagraphElement> elements) {
		List<SlpParagraphElement> result = new ArrayList<>(elements.size());

		for (ParagraphElement element : elements) {
			if (element.isCharacterRun()) {
				result.add(convertCharacterRun(element.asCharacterRun()));
			} else {
				Picture picture = element.asPicture();
				result.add(picture.asImage());
			}
		}

		return result;
	}

	private static TextFragment convertCharacterRun(CharacterRun characterRun) {
		String text = trimCarriageReturn(characterRun.getText());
		TextStyle style = TextStyle.getStyle(characterRun.isBold(), characterRun.isItalic());
		Font font = characterRun.getFont();
		TextType textType = getTextType(font, text);

		return new TextFragment(textType, style, text);
	}

	private static TextType getTextType(Font font, String text) {
		if (ONLY_ASCII_CHARS_PATTERN.matcher(text).matches()) {
			return TextType.LATIN;
		}

		return font.getRelatedTextType();
	}

	private static String trimCarriageReturn(String text) {
		return ENDS_WITH_CR_PATTERN.matcher(text).replaceFirst("");
	}

	private ParagraphElementConverter() {
		// prevent instantiation
	}
}
