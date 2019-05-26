/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.core;

/**
 * An exception to throw at unreachable code locations. Code coverage will ignore this line in
 * coverage calculations.
 */
public class UnreachableException extends FatalProgrammingException {
	private static final long serialVersionUID = 7791914858111310051L;

	/**
	 * C'tor, for unreachable statements.
	 * <p>
	 * Example:
	 * </p>
	 * 
	 * <pre>
	 * public boolean method() {
	 * 	if (condition) {
	 * 		return true;
	 * 	}
	 * 	throw new new UnreachableException("will never reach this point", e);
	 * }
	 * </pre>
	 * 
	 * @param msg a message describing what happened if the unreachable block is reached anyway.
	 *            E.g., 'unknown value'.
	 */
	public UnreachableException(String msg) {
		super(msg);
	}

	/**
	 * C'tor, for unreachable catch blocks.
	 * <p>
	 * Example:
	 * </p>
	 * 
	 * <pre>
	 * try {
	 *     ...
	 * } catch (IOException e) {
	 *     throw new UnreachableException(e);
	 * }
	 * </pre>
	 * 
	 * @param th the exception that was caught at this unreachable location.
	 */
	public UnreachableException(Throwable th) {
		super("unexpected exception caught", th);
	}

	/**
	 * C'tor, for unreachable catch blocks.
	 * <p>
	 * Example:
	 * </p>
	 * 
	 * <pre>
	 * try {
	 *     ...
	 * } catch (IOException e) {
	 *     throw new UnreachableException("IOException will never be thrown in this flow", e);
	 * }
	 * </pre>
	 * 
	 * @param msg a message describing what happened if the unreachable block is reached anyway.
	 *            E.g., 'exception impossible, since only memory I/O is used'.
	 * @param th the exception that was caught at this unreachable location.
	 */
	public UnreachableException(String msg, Throwable th) {
		super(msg, th);
	}
}
