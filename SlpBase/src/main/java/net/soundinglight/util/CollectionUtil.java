/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility to handle collections.
 * 
 */
public final class CollectionUtil {
	/**
	 * Create a modifiable list.
	 * 
	 * @param <T> the type of the items.
	 * @param items the items to add to the list.
	 * @return the list.
	 */
	@SafeVarargs
	public static <T> List<T> asModifiableList(T... items) {
		return new ArrayList<>(Arrays.<T>asList(items));
	}

	private CollectionUtil() {
		// prevent instantiation
	}

}
