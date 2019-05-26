/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */

package net.soundinglight.process;

/**
 * Exception to signal problems that occur during processing of tapelist documents.
 * 
 */
public class ProcessException extends Exception {
	private static final long serialVersionUID = 4953681765189714872L;

	/**
	 * C'tor.
	 * 
	 * @param message the failure message.
	 */
	public ProcessException(String message) {
		super(message);
	}

	/**
	 * C'tor.
	 * 
	 * @param message the failure message.
	 * @param cause the cause of the failure.
	 * 
	 */
	public ProcessException(String message, Throwable cause) {
		super(message, cause);
	}
}
