/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Log storage for test cases.
 * 
 * <p>
 * This class eats all logging below WARNING level, and fails on anything higher. Exceptions can be
 * noted using {@link #expectMsg(Level, String)}.
 * </p>
 */
public class LogStore implements TestRule {
	private final Level level;
	private FailHandler myHandler;
	private Level oldLevel;
	private List<Handler> oldHandlers = new ArrayList<>();

	/**
	 * Constructor. Sets default level.
	 */
	public LogStore() {
		this(Level.WARNING);
	}

	/**
	 * Constructor. The store appends itself to the logging configuration.
	 * 
	 * @param level The log level for this log store.
	 */
	public LogStore(Level level) {
		this.level = level;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Statement apply(Statement base, Description target) {
		return new ExceptionAwareStatement(base) {
			@Override
			protected void before() {
				setUp();
			}

			@Override
			protected void assertConditions() {
				assertIsSatisfied();
			}

			@Override
			public void after() {
				detach();
			}
		};
	}

	/**
	 * Expect a particular message on a level below the threshold.
	 * 
	 * @param msgLevel of the message to expect
	 * @param message expected message
	 */
	public void expectMsg(Level msgLevel, String message) {
		updateLevel(msgLevel);
		myHandler.expectMsg(msgLevel, message);
	}

	void setUp() {
		Logger rootLogger = Logger.getLogger("");
		oldLevel = rootLogger.getLevel();

		Collections.addAll(oldHandlers, rootLogger.getHandlers());

		for (Handler h : oldHandlers) {
			rootLogger.removeHandler(h);
		}
		rootLogger.setLevel(level);

		myHandler = new FailHandler();
		myHandler.setLevel(level);

		rootLogger.addHandler(myHandler);
	}

	void assertIsSatisfied() {
		myHandler.assertExpectedMsg();
	}

	void detach() {
		LogManager.getLogManager().reset();

		Logger rootLogger = Logger.getLogger("");
		rootLogger.setLevel(oldLevel);
		for (Handler h : oldHandlers) {
			rootLogger.addHandler(h);
		}
		oldHandlers.clear();
	}

	private void updateLevel(Level updatedLevel) {
		Logger logger = Logger.getLogger("");
		if (updatedLevel.intValue() < logger.getLevel().intValue()) {
			// make sure the appropriate messages make it to the handler
			logger.setLevel(updatedLevel);
		}
	}
}
