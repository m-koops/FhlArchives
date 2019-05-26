/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import static org.junit.Assert.assertEquals;

import static net.soundinglight.AssertExt.assertBooleanEquals;

import static net.soundinglight.poi.bo.ParagraphElementTestUtil.*;
import net.soundinglight.jaxb.MarshalTestUtil;

import org.junit.Test;

public class CharacterRunTest {
	private static final String SERIALIZED_CHARACTER_RUN_RESOURCE =
			"../poi/bo/serializedCharacterRun.xml";
	private static final CharacterRun SAMPLE = createRun("sample", Font.SL_SOULWORD, true, false);

	@Test
	public void testGetters() {
		assertEquals("sample", SAMPLE.getText());
		assertEquals(Font.SL_SOULWORD, SAMPLE.getFont());
		assertBooleanEquals(true, SAMPLE.isBold());
		assertBooleanEquals(false, SAMPLE.isItalic());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_CHARACTER_RUN_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		CharacterRun instance =
				MarshalTestUtil.unmarshal(SERIALIZED_CHARACTER_RUN_RESOURCE, CharacterRun.class);
		ParagraphElementTestUtil.assertCharacterRunEquals(SAMPLE, instance);
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("sample", SAMPLE.toString());
	}

	@Test
	public void testShouldBeBlankWhenEmpty() {
		assertBooleanEquals(true, createNormalCharRun("").isBlank());
	}

	@Test
	public void testShouldBeBlankWhenOnlyWhitespaceContent() {
		assertBooleanEquals(true, createNormalCharRun(" \t  ").isBlank());
	}

	@Test
	public void testShouldNotBeBlankWithNonWhitespaceContent() {
		assertBooleanEquals(false, createNormalCharRun(" abc\t").isBlank());
	}

	@Test
	public void testShouldJoinTextContentTakingLeftRunsCharacteristics() {
		CharacterRun one = createRun("one", Font.SL_SOULWORD, true, false);
		CharacterRun two = createItalicCharRun("two");
		ParagraphElementTestUtil.assertCharacterRun(CharacterRun.join(one, two), "onetwo",
				Font.SL_SOULWORD, true, false);
	}

	@Test
	public void testShouldJoinTextContentAndTakeRightRunCharacteristics() {
		CharacterRun one = createRun("\t", Font.SL_SOULWORD, true, false);
		CharacterRun two = createItalicCharRun("two");
		ParagraphElementTestUtil.assertCharacterRun(CharacterRun.join(one, two), "\ttwo",
				Font.TIMES, false, true);
	}

	@Test
	public void testShouldJoinWhitespaceContentTakingLeftRunsCharacteristics() {
		CharacterRun one = createRun(" ", Font.SL_SOULWORD, true, false);
		CharacterRun two = createItalicCharRun("\t");
		ParagraphElementTestUtil.assertCharacterRun(CharacterRun.join(one, two), " \t",
				Font.SL_SOULWORD, true, false);
	}
}
