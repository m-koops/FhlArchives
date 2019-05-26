/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */

package net.soundinglight.parse;

/**
 * Exception to signal problems that occur during parsing of tapelist documents.
 * 
 */
public class ParseException extends Exception {
	private static final long serialVersionUID = -911837657613526661L;

	/**
	 * C'tor.
	 * 
	 * @param message the failure message.
	 */
	public ParseException(String message) {
		super(message);
	}

	/**
	 * C'tor.
	 * 
	 * @param message the failure message.
	 * @param cause the cause of the failure.
	 * 
	 */
	public ParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
