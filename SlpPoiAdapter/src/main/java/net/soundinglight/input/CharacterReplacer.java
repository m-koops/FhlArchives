/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.input;

import java.util.ArrayList;
import java.util.List;

import net.soundinglight.poi.CharacterReplacement;
import net.soundinglight.poi.CharacterReplacementMap;
import net.soundinglight.poi.ExtendedCharacterUtil;
import net.soundinglight.poi.bo.CharacterRun;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.poi.bo.ParagraphElement;

/**
 * Replaces non-Unicode characters.
 */
public final class CharacterReplacer {
	/**
	 * Replace non-Unicode characters.
	 *
	 * @param paragraphs the paragraphs to replace characters of.
	 * @return the paragraphs with replaced characters.
	 */
	public static List<Paragraph> replaceChars(List<Paragraph> paragraphs) {
		List<Paragraph> result = new ArrayList<>(paragraphs.size());
		for (Paragraph paragraph : paragraphs) {
			result.add(replaceChars(paragraph));
		}
		return result;
	}

	private static Paragraph replaceChars(Paragraph paragraph) {
		List<ParagraphElement> elements = new ArrayList<>(paragraph.getElements().size());
		for (ParagraphElement element : paragraph.getElements()) {
			if (element.isCharacterRun()) {
				element = replaceChars(element.asCharacterRun());
			}

			elements.add(element);
		}
		return new Paragraph(paragraph.getId(), paragraph.getTextAlignment(), paragraph.getIndentFromLeft(),
				paragraph.getFirstLineIndent(), elements);
	}

	private static CharacterRun replaceChars(CharacterRun run) {
		CharacterReplacementMap characterReplacements = run.getFont().getCharacterReplacements();
		String text = replaceChars(run.getText(), characterReplacements);
		text = ExtendedCharacterUtil.encodeUnsupportedChars(text, characterReplacements);
		return new CharacterRun(text, run.getFont(), null, run.isBold(), run.isItalic());
	}

	private static String replaceChars(String text, CharacterReplacementMap replacementMap) {
		for (CharacterReplacement replacement : replacementMap.getReplacements()) {
			text = text.replace(replacement.getOriginal(), replacement.getReplacement());
		}
		return text;
	}

	private CharacterReplacer() {
		// prevent instantiation
	}
}
