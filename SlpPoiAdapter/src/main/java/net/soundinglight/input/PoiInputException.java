/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */

package net.soundinglight.input;

/**
 * Exception to signal problems that occur during the loading of POI documents.
 * 
 */
public class PoiInputException extends Exception {
	private static final long serialVersionUID = -5720131463078750109L;

	/**
	 * C'tor.
	 * 
	 * @param message the failure message.
	 */
	public PoiInputException(String message) {
		super(message);
	}

	/**
	 * C'tor.
	 * 
	 * @param message the failure message.
	 * @param cause the cause of the failure.
	 * 
	 */
	public PoiInputException(String message, Throwable cause) {
		super(message, cause);
	}
}
