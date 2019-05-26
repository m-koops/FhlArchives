/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Utility to track occurrences of extended characters in tapelist texts.
 */
public final class ExtendedCharacterUtil {
	private static final String ALLOWED_EXTENDED_LATIN1_CHARS =
			"\u00C4\u00CB\u00CF\u00D6\u00DC\u00E4\u00EB\u00EF\u00F6\u00FC"
					+ "\u00C1\u00C9\u00CD\u00D3\u00DA\u00E1\u00E9\u00ED\u00F3\u00FA"
					+ "\u00C7\u00E7\u00B0\u00A9";
	private static final String ALLOWED_SPECIAL_CHARS = "\u2026\u2190\u2191\u2192\u2193";

	/**
	 * Shows Unicode values of chars in the provided text.
	 * 
	 * @param text the provided text
	 * @return the text embellished with the Unicode values.
	 */
	public static String showUnicodeValues(String text) {
		List<String> unicodeChars = new ArrayList<>();

		for (char ch : text.toCharArray()) {
			unicodeChars.add(ch + "(" + encodeCharacter(ch) + ")");
		}

		return text + " " + unicodeChars;
	}

	/**
	 * Encode non-supported characters for the given character replacements in a provided text.
	 * 
	 * @param text the provided text.
	 * @param characterReplacements the character replacements that apply.
	 * @return the text with encoded non-supported characters.
	 */
	public static String encodeUnsupportedChars(String text,
			CharacterReplacementMap characterReplacements) {
		String replacedChars = determineReplacedChars(characterReplacements);

		StringBuilder buf = new StringBuilder();
		for (char ch : text.toCharArray()) {
			buf.append(isSupportedCharacter(ch, replacedChars) ? Character.valueOf(ch) : "{"
					+ encodeCharacter(ch) + "}");
		}

		return buf.toString();
	}

	private static boolean isSupportedCharacter(char ch, String replacedChars) {
		return isSupportedLatin1Character(ch)
				|| replacedChars.contains(new Character(ch).toString());
	}

	private static boolean isSupportedLatin1Character(char ch) {
		// ascii control characters
		if (ch < ' ' && ch != '\t') {
			return false;
		}

		if (ch < '\u007F') {
			return true;
		}

		if (ALLOWED_EXTENDED_LATIN1_CHARS.contains(String.valueOf(ch))) {
			return true;
		}

		if (ALLOWED_SPECIAL_CHARS.contains(String.valueOf(ch))) {
			return true;
		}

		return false;
	}

	private static String encodeCharacter(char ch) {
		return String.format("\\u%04X", Integer.valueOf(ch));
	}

	private static String determineReplacedChars(CharacterReplacementMap characterReplacements) {
		Set<CharacterReplacement> replacements = characterReplacements.getReplacements();
		StringBuilder buf = new StringBuilder();
		for (CharacterReplacement replacement : replacements) {
			buf.append(replacement.getReplacement());
		}
		return buf.toString();
	}

	private ExtendedCharacterUtil() {
		// prevent instantiation
	}
}
