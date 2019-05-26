/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static net.soundinglight.AssertExt.assertBooleanEquals;
import static org.junit.Assert.*;

public class TextFragmentTest {
	private static final TextFragment SAMPLE = new TextFragment(TextType.LATIN, TextStyle.BOLD,
			"first");
	private static final String SERIALIZED_RESOURCE = "../bo/serializedTextFragment.xml";

	@Test
	public void testGetters() {
		TextFragmentTestUtil.assertTextFragment(SAMPLE, TextType.LATIN, TextStyle.BOLD, "first");
		assertEquals("first", SAMPLE.getText());
		assertEquals(TextType.LATIN, SAMPLE.getType());
		assertEquals(TextStyle.BOLD, SAMPLE.getStyle());
		assertTrue(SAMPLE.hasSignificantContent());
		assertFalse(SAMPLE.isEmpty());
		assertFalse(SAMPLE.isBlank());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		TextFragmentTestUtil.assertTextFragmentEquals(SAMPLE,
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, TextFragment.class));
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("first (latin/bold)", SAMPLE.toString());
	}

	@Test
	public void testShouldBeEmpty() {
		assertBooleanEquals(true, new TextFragment("").isEmpty());
	}

	@Test
	public void testShouldNotBeEmptyWhenOnlyWhitespaceContent() {
		assertBooleanEquals(false, new TextFragment(" \t  ").isEmpty());
	}

	@Test
	public void testShouldNotBeEmptyWithNonWhitespaceContent() {
		assertBooleanEquals(false, new TextFragment(" abc\t").isEmpty());
	}

	@Test
	public void testShouldBeBlankWhenEmpty() {
		assertBooleanEquals(true, new TextFragment("").isBlank());
	}

	@Test
	public void testShouldBeBlankWhenOnlyWhitespaceContent() {
		assertBooleanEquals(true, new TextFragment(" \t  ").isBlank());
	}

	@Test
	public void testShouldNotBeBlankWithNonWhitespaceContent() {
		assertBooleanEquals(false, new TextFragment(" abc\t").isBlank());
	}

	@Test
	public void testShouldNotBeSignificantWhenEmpty() {
		assertBooleanEquals(false, new TextFragment("").hasSignificantContent());
	}

	@Test
	public void testShouldNotBeSignificantWhenOnlyWhitespaceContent() {
		assertBooleanEquals(false, new TextFragment(" \t  ").hasSignificantContent());
	}

	@Test
	public void testShouldNotBeSignificantWhenOnlyPunctuationContent() {
		assertBooleanEquals(false, new TextFragment(";").hasSignificantContent());
	}

	@Test
	public void testShouldBeSignificantWithNonWhitespaceContent() {
		assertBooleanEquals(true, new TextFragment(" abc\t.").hasSignificantContent());
	}

	@Test
	public void testShouldJoinTextContentTakingLeftRunsCharacteristics() {
		TextFragment result = TextFragment.join(SAMPLE, new TextFragment("second"));
		TextFragmentTestUtil.assertTextFragment(result, TextType.LATIN, TextStyle.BOLD,
				"firstsecond");
	}

	@Test
	public void testShouldJoinTextContentAndTakeRightRunCharacteristics() {
		TextFragment first = new TextFragment(TextType.LATIN, TextStyle.BOLD, "\t");
		TextFragment second = new TextFragment(TextType.SACRED, TextStyle.ITALIC, "text");
		TextFragmentTestUtil.assertTextFragment(TextFragment.join(first, second), TextType.SACRED,
				TextStyle.ITALIC, "\ttext");
	}

	@Test
	public void testShouldJoinWhitespaceContentTakingLeftRunsCharacteristics() {
		TextFragment first = new TextFragment(TextType.LATIN, TextStyle.BOLD, " ");
		TextFragment second = new TextFragment(TextType.SACRED, TextStyle.ITALIC, "\t");
		TextFragmentTestUtil.assertTextFragment(TextFragment.join(first, second), TextType.LATIN,
				TextStyle.BOLD, " \t");
	}
}
