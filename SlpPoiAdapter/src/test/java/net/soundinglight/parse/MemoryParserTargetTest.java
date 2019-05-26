/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import static org.junit.Assert.assertSame;

import java.util.Collections;

import net.soundinglight.bo.Event;
import net.soundinglight.bo.SlpSession;
import net.soundinglight.bo.SlpParagraph;

import org.junit.Test;

public class MemoryParserTargetTest {
	@Test
	public void testShouldYieldCapturedParseResult() {
		Event event =
				new Event("", Collections.<SlpParagraph>emptyList(),
						Collections.<SlpParagraph>emptyList(), Collections.<SlpSession>emptyList());

		MemoryParserTarget target = new MemoryParserTarget();
		target.setEvent(event);
		assertSame(event, target.getEvent());
	}

}
