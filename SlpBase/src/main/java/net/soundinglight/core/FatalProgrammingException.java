/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.core;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A programming error that will cause havoc.
 */
public class FatalProgrammingException extends RuntimeException {
	private static final long serialVersionUID = 2951336270226961778L;
	private static final Logger LOG = Logger.getLogger(FatalProgrammingException.class.getName());

	/**
	 * Constructor.
	 * 
	 * @param msg message
	 */
	public FatalProgrammingException(String msg) {
		super(msg);

		LOG.severe("programming error: " + msg);
	}

	/**
	 * Constructor.
	 * 
	 * @param msg message
	 * @param th nested throwable
	 */
	public FatalProgrammingException(String msg, Throwable th) {
		super(msg, th);

		LOG.log(Level.SEVERE, "programming error: " + msg, th);
	}
}
