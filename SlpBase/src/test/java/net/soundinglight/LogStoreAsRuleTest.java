/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Rule;
import org.junit.Test;

public class LogStoreAsRuleTest {
	private static final Logger LOG = Logger.getLogger(LogStoreAsRuleTest.class.getName());

	@Rule
	public final LogStore store = new LogStore();

	@Test
	public void testShouldAcceptExpectedMessage() {
		store.expectMsg(Level.SEVERE, "magic");
		LOG.severe("deliberate magic error");
	}
}
