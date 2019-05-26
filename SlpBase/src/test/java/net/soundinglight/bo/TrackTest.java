/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class TrackTest {
	private static final String SERIALIZED_RESOURCE = "../bo/serializedTrack.xml";
	private static final List<SlpParagraph> PARAGRAPHS = Arrays.asList(
			new SlpParagraph(1,
					Arrays.asList(TextFragmentTestUtil.NORMAL, ImageTest.SAMPLE, TextFragmentTestUtil.SACRED),
					Alignment.LEFT, true, "paragraph1", null),
			new SlpParagraph(2, Arrays.asList(TextFragmentTestUtil.ITALIC, TextFragmentTestUtil.BOLD_WHITESPACE),
					Alignment.JUSTIFY, false, "paragraph2", null));
	public static final Track SAMPLE = new Track(10, Integer.valueOf(3), PARAGRAPHS);

	@Test
	public void testGetters() {
		assertEquals(10, SAMPLE.getDuration());
		assertEquals(Integer.valueOf(3), SAMPLE.getNumber());
		assertSame(PARAGRAPHS, SAMPLE.getParagraphs());
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		TrackTestUtil.assertTrackEquals(SAMPLE, MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, Track.class));
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals(
				"10; 3; [id=1; 'paragraph1'; true; [latin (latin/normal), "
						+ "image/jpeg; size= 8.3*13.1 (w*h in mm); content=dGVz...ZW50, sacred (sacred/normal)], "
						+ "id=2; 'paragraph2'; false; [italic (latin/italic)," + " 	 (latin/bold)]]",
				SAMPLE.toString());
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentationForTrackWithoutNumber() {
		assertEquals(
				"0; <no number>; [id=1; 'paragraph1'; true; [latin (latin/normal), "
						+ "image/jpeg; size= 8.3*13.1 (w*h in mm); content=dGVz...ZW50, sacred (sacred/normal)], "
						+ "id=2; 'paragraph2'; false; [italic (latin/italic), 	 (latin/bold)]]",
				new Track(0, null, PARAGRAPHS).toString());
	}
}
