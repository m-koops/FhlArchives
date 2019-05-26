/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import net.soundinglight.bo.SlpSession;
import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.parse.strategy.ParserStrategy2005;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.poi.bo.Section;

public class SectionParserTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testShouldParseSection() throws Exception {
		List<Section> sections = loadDocument("/net/soundinglight/sample.no-preamble.poi.xml");
		List<SlpSession> sessions = createSectionParser().parseSections(sections);
		assertEquals(1, sessions.size());
		assertEquals("1", sessions.get(0).getDetails().getId());
	}

	private SectionParser createSectionParser() {
		return new SectionParser(new ParserStrategy2005(), 1982);
	}

	private List<Section> loadDocument(String resource) throws Exception {
		Document document = MarshalTestUtil.unmarshal(resource, Document.class);
		List<Section> sections = Arrays.asList(new Section(document.getParagraphs()));
		return sections;
	}

}
