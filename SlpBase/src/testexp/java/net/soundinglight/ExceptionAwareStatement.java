/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import org.junit.ComparisonFailure;
import org.junit.runners.model.Statement;

/**
 * A statement that correctly handles thrown exceptions.
 */
public abstract class ExceptionAwareStatement extends Statement {
	private final Statement base;

	public ExceptionAwareStatement(Statement base) {
		this.base = base;
	}

	@Override
	public void evaluate() throws Throwable {
		try {
			try {
				before();
				base.evaluate();
			} catch (Throwable e) {
				throwIfAssertionError(e);
				assertConditions();
				throw e;
			}
			assertConditions();
		} finally {
			after();
		}
	}

	private void throwIfAssertionError(Throwable e) throws Throwable {
		throwComparisonFailureIfPresent(e);
		if (e instanceof AssertionError) {
			throw e;
		}
	}

	protected abstract void assertConditions() throws Throwable;

	protected void before() throws Throwable {
		// no operation by default
	}

	protected void after() throws Throwable {
		// no operation by default
	}

	private void throwComparisonFailureIfPresent(Throwable e) throws Throwable {
		if (e instanceof ComparisonFailure) {
			throw e;
		}
		Throwable cause = e.getCause();
		if (cause == null) {
			return;
		}
		throwComparisonFailureIfPresent(cause);
	}
}
