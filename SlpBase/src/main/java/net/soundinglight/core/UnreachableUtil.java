/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.core;

/**
 * Utility to wrap a code block that throws unreachable exceptions
 * 
 * This will avoid a drop in branch coverage. Note that line coverage will still go down. All
 * methods in this class are excluded using the <code>&lt;ignore&gt;</code> syntax for cobertura.
 */
public final class UnreachableUtil {
	/**
	 * Suppress an exception that is never thrown.
	 * 
	 * @param <T> return type.
	 * @param <E> exception type.
	 * @param closure closure to execute.
	 * @return the return value.
	 */
	public static <T, E extends Throwable> T suppressException(CallableT<T, E> closure) {
		try {
			return closure.call();
		} catch (Throwable e) {
			throw new UnreachableException(e);
		}
	}

	private UnreachableUtil() {
		// empty to avoid instantiation
	}
}
