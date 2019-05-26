/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.CheckForNull;

/**
 * String utility methods.
 * 
 */
public final class StringUtil {
	/**
	 * Turn <code>null</code> into an empty {@link String}.
	 * 
	 * @param value the value that may be <code>null</code>.
	 * @return the value, or empty {@link String} if value is <code>null</code>.
	 */
	public static String emptyIfNull(@CheckForNull String value) {
		return value == null ? "" : value;
	}

	/**
	 * Checks whether a given value is <code>null</code> or an empty {@link String}.
	 * 
	 * @param value the value to check.
	 * @return <code>true</code> when value is <code>null</code> or an empty {@link String}.
	 */
	public static boolean isNullOrEmpty(@CheckForNull String value) {
		return value == null || value.isEmpty();
	}

	/**
	 * Turns a collection into a list of {@link String} representations of items in that collection.
	 * 
	 * @param items the collection of items.
	 * @return a list of {@link String} representations.
	 */
	public static List<String> asStringList(Collection<?> items) {
		List<String> result = new ArrayList<>(items.size());
		for (Object item : items) {
			result.add(item.toString());
		}
		return result;
	}

	/**
	 * Normalize new-line characters.
	 * 
	 * @param raw the raw {@link String}.
	 * @return the normalized {@link String}.
	 */
	public static String normalizeNewLine(String raw) {
		return raw.replaceAll("\r\n", "\n");
	}

	/**
	 * Join strings with a separator.
	 * 
	 * @param strings the strings to join
	 * @param sep the separator string
	 * @return the joined string
	 */
	public static String join(Collection<String> strings, String sep) {
		Iterator<String> it = strings.iterator();

		if (!it.hasNext()) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		String str = it.next();
		if (str != null) {
			builder.append(str);
		}

		while (it.hasNext()) {
			builder.append(sep);
			str = it.next();
			if (str != null) {
				builder.append(str);
			}
		}
		return builder.toString();
	}

	private StringUtil() {
		// prevent instantiation
	}
}
