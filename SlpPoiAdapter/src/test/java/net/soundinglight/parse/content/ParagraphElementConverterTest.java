/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.content;

import static net.soundinglight.AssertExt.assertPrivateCtor;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import net.soundinglight.bo.Image;
import net.soundinglight.bo.SlpParagraphElement;
import net.soundinglight.bo.TextFragment;
import net.soundinglight.bo.TextStyle;
import net.soundinglight.bo.TextType;
import net.soundinglight.poi.bo.CharacterRun;
import net.soundinglight.poi.bo.ParagraphElementTestUtil;
import net.soundinglight.poi.bo.Picture;

public class ParagraphElementConverterTest {
	private static final String GAYATRI = "G\u0257;yatr\u0299;";
	private static final CharacterRun GAYATRI_RUN = ParagraphElementTestUtil.createSlSoulwordCharRun(GAYATRI);
	private static final CharacterRun MANTRA_RUN = ParagraphElementTestUtil.createNormalCharRun(" mantra");

	@Test
	public void testShouldConvertCharacterRunToTextFragment() {
		List<SlpParagraphElement> elements = ParagraphElementConverter.convert(Collections.singletonList(GAYATRI_RUN));

		assertSingleFragment(elements, GAYATRI, TextStyle.NORMAL, TextType.SACRED_CAPS);
	}

	private void assertSingleFragment(List<SlpParagraphElement> elements, String text, TextStyle style, TextType type) {
		assertEquals(1, elements.size());
		SlpParagraphElement first = elements.get(0);
		assertTrue("element should be a TextFragment", first.isTextFragment());
		TextFragment fragment = first.asTextFragment();

		assertEquals(text, fragment.getText());
		assertEquals(style, fragment.getStyle());
		assertEquals(type, fragment.getType());
	}

	@Test
	public void testShouldConvertCharacterRunsToTextFragments() {
		List<SlpParagraphElement> elements = ParagraphElementConverter.convert(Arrays.asList(GAYATRI_RUN, MANTRA_RUN));

		assertEquals(GAYATRI, elements.get(0).asTextFragment().getText());
		assertEquals(" mantra", elements.get(1).asTextFragment().getText());
	}

	@Test
	public void testShouldStripOffTrailingCarriageReturn() {
		CharacterRun run = ParagraphElementTestUtil.createNormalCharRun("test \r");
		List<SlpParagraphElement> elements = ParagraphElementConverter.convert(Collections.singletonList(run));
		assertSingleFragment(elements, "test ", TextStyle.NORMAL, TextType.LATIN);
	}

	@Test
	public void testShouldYieldLatinTextTypeForJustAsciiChars() throws Exception {
		CharacterRun run = ParagraphElementTestUtil.createSlSoulwordCharRun("test");
		List<SlpParagraphElement> elements = ParagraphElementConverter.convert(Collections.singletonList(run));
		assertSingleFragment(elements, "test", TextStyle.NORMAL, TextType.LATIN);

	}

	@Test
	public void testShouldConvertPictureToImage() {
		Picture picture = ParagraphElementTestUtil.createTestPictureElement();
		List<SlpParagraphElement> elements = ParagraphElementConverter.convert(Collections.singletonList(picture));
		assertEquals(1, elements.size());
		SlpParagraphElement first = elements.get(0);
		assertTrue("element should be an Image", first.isImage());
		Image image = first.asImage();
		assertEquals(picture.getMimeType(), image.getMimeType());
		assertArrayEquals(picture.getBytes(), image.getBytes());
	}

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(ParagraphElementConverter.class);
	}
}
