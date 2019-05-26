package net.soundinglight.parse;

import static org.junit.Assert.assertSame;

import java.util.Collections;

import org.junit.Test;

import net.soundinglight.poi.bo.Document;

public class MemoryParserSourceTest {
	@Test
	public void testShouldPassthroughDocument() {
		Document document = new Document("test", Collections.emptyList());

		assertSame(document, new MemoryParserSource(document).getDocument());
	}
}
