/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi;

/**
 * Defines a replacement of a non-supported extended character with a supported one.
 */
public class CharacterReplacement {
	private final String original;
	private final String replacement;

	/**
	 * C'tor.
	 * 
	 * @param original the character to replace.
	 * @param replacement the replacement character.
	 */
	public CharacterReplacement(String original, String replacement) {
		this.original = original;
		this.replacement = replacement;
	}

	/**
	 * @return the original.
	 */
	public String getOriginal() {
		return original;
	}

	/**
	 * @return the replacement.
	 */
	public String getReplacement() {
		return replacement;
	}
}
