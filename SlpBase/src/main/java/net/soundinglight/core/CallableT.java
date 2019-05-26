/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.core;

/**
 * Callable that throws a single exception type.
 * 
 * @param <T> return type.
 * @param <E> exception type.
 */
public interface CallableT<T, E extends Throwable> {
	/**
	 * Execute the operation.
	 * 
	 * @return the return value.
	 * @throws E exception that is never thrown.
	 */
	// CHECKSTYLE:OFF:RedundantThrows
	T call() throws E;
	// CHECKSTYLE:ON:RedundantThrows
}
