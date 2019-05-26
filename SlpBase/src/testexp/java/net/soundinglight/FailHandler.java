/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * A handler that fails on a given message and level.
 */
class FailHandler extends Handler {
	private static final Formatter FORMATTER = new SimpleFormatter();

	private static class ExpectedMessage {

		private final Level level;
		private final String message;

		public ExpectedMessage(Level level, String Message) {
			this.level = level;
			message = Message;
		}

		public Level getLevel() {
			return level;
		}

		public String getMessage() {
			return message;
		}
	}

	private final List<ExpectedMessage> expectedMessages = new ArrayList<>();
	private final List<String> illegalMessages = new ArrayList<>();
	private CountDownLatch remaining = new CountDownLatch(0);

	/**
	 * C'tor.
	 */
	FailHandler() {
		setFormatter(FORMATTER);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		// no operation
	}

	/**
	 * Assert that all expected messages have been emitted.
	 */
	public synchronized void assertExpectedMsg() {
		try {
			assertTrue("these illegal messages were logged : " + illegalMessages,
					illegalMessages.isEmpty());

			assertTrue("not all expected messages emitted; remaining: " + expectedMessages,
					expectedMessages.isEmpty());
		} finally {
			expectedMessages.clear();
			illegalMessages.clear();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void flush() {
		// no-op
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void publish(LogRecord record) {
		if (isLoggable(record)) {
			String msg =
					"illegal message in thread " + Thread.currentThread().getName() + " ["
							+ formatMessage(record) + "]";
			illegalMessages.add(msg);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized boolean isLoggable(LogRecord record) {
		String actualMsg = formatMessage(record);

		Iterator<ExpectedMessage> iter = expectedMessages.iterator();
		while (iter.hasNext()) {
			ExpectedMessage expected = iter.next();
			if (doesRecordMatchExpectedMessage(record, actualMsg, expected)) {
				iter.remove();
				remaining.countDown();
				return false;
			}
		}

		return super.isLoggable(record);
	}

	private boolean doesRecordMatchExpectedMessage(LogRecord record, String actualMsg,
			ExpectedMessage expected) {
		return record.getLevel() == expected.getLevel()
				&& actualMsg.contains(expected.getMessage());
	}

	private String formatMessage(LogRecord record) {
		return getFormatter().format(record).replace("\r", "");
	}

	/**
	 * Expect a message.
	 * 
	 * @param level the level to expect
	 * @param message message to expect
	 */
	public synchronized void expectMsg(Level level, String message) {
		expectedMessages.add(new ExpectedMessage(level, message));
		remaining = new CountDownLatch(expectedMessages.size());
	}

	/**
	 * Wait for all messages to arrive.
	 * 
	 * @param millis time in milliseconds to wait for messages to arrive
	 * @return whether all messages have been emitted
	 * @throws InterruptedException when interrupted
	 */
	public boolean waitForAllMessages(int millis) throws InterruptedException {
		return remaining.await(millis, TimeUnit.MILLISECONDS);
	}
}
