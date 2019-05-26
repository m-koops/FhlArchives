/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi;

import static net.soundinglight.UnicodeConstants.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Map of character replacements.
 * 
 */
public final class CharacterReplacementMap {
	public static final CharacterReplacementMap OTHER_FONT_REPLACEMENTS =
			createOtherFontReplacements();
	public static final CharacterReplacementMap TIMES_REPLACEMENTS = createTimesReplacements();
	public static final CharacterReplacementMap ZAPF_DINGBATS_REPLACEMENTS =
			createZapfDingbatsReplacements();
	public static final CharacterReplacementMap SL_SOULWORD_REPLACEMENTS =
			createSlSoulwordReplacements();
	public static final CharacterReplacementMap SL_CASWORD_REPLACEMENTS =
			createSlCaswordReplacements();

	private final Set<CharacterReplacement> replacements = new HashSet<>();

	private void addReplacement(char original, char replacement) {
		addReplacement(String.valueOf(original), String.valueOf(replacement));
	}

	private void addReplacement(String original, String replacement) {
		replacements.add(new CharacterReplacement(original, replacement));
	}

	/**
	 * @return the replacements.
	 */
	public Set<CharacterReplacement> getReplacements() {
		return replacements;
	}

	private void addCommonReplacements() {
		addReplacement("\r", ""); // end of line character
		addReplacement('\u00A0', ' '); // some special form of space
		addReplacement('\u2018', '\''); // backward single-quote
		addReplacement('\u2019', '\''); // forward single-quote
		addReplacement('\u201C', '\"'); // backward double-quote
		addReplacement('\u201D', '\"'); // forward double-quote

		addReplacement('\uF02B', '+'); // recommendation marker
		addReplacement('\u000B', '\n'); // soft line break in Word
		addReplacement('\u000C', '\n'); // hard page break in Word
		addReplacement("\u001F", ""); // hidden control character in Word, ignore
		addReplacement("\u0001", ""); // hidden control character in table, ignore

		addReplacement('\uF033', SQR_ROOT); // recommendation marker in ZapfDingbats not always
											// extracted with that font. Unclear why.

		// TODO 2014-07-15 Fix these open issues in character replacements
		addReplacement('\u0005', '\u00B6'); // comment marker in session 42-70
		addReplacement('\u0007', '\t'); // table -> next cell marker
		addReplacement('\u220F', '\u00B6'); // SLP Symbols char in session 42-70
	}

	private static CharacterReplacementMap createTimesReplacements() {
		CharacterReplacementMap map = new CharacterReplacementMap();
		map.addReplacement('\u2248', '='); // double tilde sign in diagram, session 1047
		map.addReplacement('\u2206', '\u00B6'); // delta sign in diagram, session 1047
		map.addReplacement('\u00C5', '\u00FC'); // lower case u umlaut
		map.addReplacement('\u00B8', '-');
		map.addReplacement('\u2013', '-'); // extra long dash in Word
		map.addReplacement('\u2022', ' '); // extra bold dot in Word
		map.addReplacement('\uF020', ' '); // non-visible marker in session 2884 header
		map.addReplacement('\u02DA', '\u00B0'); // degree sign

		return map;
	}

	private static CharacterReplacementMap createZapfDingbatsReplacements() {
		CharacterReplacementMap map = new CharacterReplacementMap();
		map.addReplacement('\uF066', '*');
		map.addReplacement("\uF060", "*"); // flower symbol at end of session 2835
		map.addReplacement('\uF076', '*');
		map.addReplacement('\uF020', ' ');
		map.addReplacement('3', SQR_ROOT);
		map.addReplacement("\uF06C", ""); // bullet character from FYA 2052-2126
		return map;
	}

	private static CharacterReplacementMap createSlCaswordReplacements() {
		CharacterReplacementMap map = new CharacterReplacementMap();
		map.addReplacement('\u00C5', MACRON_UPPER_A);
		map.addReplacement('\u00D4', MACRON_UPPER_I);
		map.addReplacement('\u00D8', MACRON_UPPER_O);
		map.addReplacement('\u25CA', MACRON_UPPER_U);

		map.addReplacement('\u00E5', MACRON_LOWER_A);
		map.addReplacement('\u0192', MACRON_LOWER_E);
		map.addReplacement('\u2206', MACRON_LOWER_I);
		map.addReplacement('\u00F8', MACRON_LOWER_O);
		map.addReplacement('\u221A', MACRON_LOWER_U);

		map.addReplacement('\u0178', MACRON_DOT_UNDER_UPPER_L);
		map.addReplacement('\u0152', MACRON_DOT_UNDER_UPPER_R);

		map.addReplacement('\u00DB', DOT_ABOVE_UPPER_M);
		map.addReplacement('\u2126', DOT_ABOVE_LOWER_M);
		map.addReplacement('\u00A5', DOT_ABOVE_LOWER_N);
		map.addReplacement('\u02C6', DOT_ABOVE_LOWER_NG);

		map.addReplacement('\u00CE', DOT_UNDER_UPPER_D);
		map.addReplacement('\u00D2', DOT_UNDER_UPPER_L);
		map.addReplacement('\u00D9', DOT_UNDER_UPPER_N);
		map.addReplacement('\u00C2', DOT_UNDER_UPPER_R);
		map.addReplacement('\u2020', DOT_UNDER_UPPER_T);

		map.addReplacement('\u02D9', DOT_UNDER_LOWER_H);
		map.addReplacement('\u2202', DOT_UNDER_LOWER_D);
		map.addReplacement('\u2248', DOT_UNDER_LOWER_N);
		map.addReplacement('\u00AE', DOT_UNDER_LOWER_R);
		map.addReplacement('\u00DF', DOT_UNDER_LOWER_S);

		map.addReplacement('\u00B0', ACUTE_UPPER_S);
		map.addReplacement('\u2022', ACUTE_UPPER_S);

		map.addReplacement('\u2211', TILDE_LOWER_N);
		map.addReplacement('\u2264', '\u00F6'); // lower case o umlaut

		return map;
	}

	private static CharacterReplacementMap createSlSoulwordReplacements() {
		CharacterReplacementMap map = new CharacterReplacementMap();
		map.addReplacement('\u00E5', MACRON_UPPER_A);
		map.addReplacement('\u00C5', MACRON_UPPER_A);
		map.addReplacement('\u0192', MACRON_UPPER_E);
		map.addReplacement('\u00D4', MACRON_UPPER_I);
		map.addReplacement('\u2206', MACRON_UPPER_I);
		map.addReplacement('\u00D8', MACRON_UPPER_O);
		map.addReplacement('\u221A', MACRON_UPPER_U);

		map.addReplacement('\u00F8', MACRON_LOWER_O);

		map.addReplacement('\u2022', ACUTE_UPPER_S);

		map.addReplacement('\u2126', DOT_ABOVE_UPPER_M);
		map.addReplacement('\u02C6', DOT_ABOVE_UPPER_NG);

		map.addReplacement('\u2202', DOT_UNDER_UPPER_D);
		map.addReplacement('\u02D9', DOT_UNDER_UPPER_H);
		map.addReplacement('\u2248', DOT_UNDER_UPPER_N);
		map.addReplacement('\u00C2', DOT_UNDER_UPPER_R);
		map.addReplacement('\u2020', DOT_UNDER_UPPER_T);

		map.addReplacement('\u2211', TILDE_UPPER_A);

		map.addReplacement('\uF020', ' ');
		return map;
	}

	private static CharacterReplacementMap createOtherFontReplacements() {
		CharacterReplacementMap map = new CharacterReplacementMap();
		map.addReplacement('\u2022', ' '); // extra bold dot in Word
		// map.addReplacement('', '\u2190'); // arrow left
		map.addReplacement('\uF08F', '\u2191'); // arrow up
		// map.addReplacement('', '\u2192'); // arrow right
		map.addReplacement('\u2044', '\u2193'); // arrow down
		return map;
	}

	private CharacterReplacementMap() {
		// prevent external instantiation
		addCommonReplacements();
	}
}
