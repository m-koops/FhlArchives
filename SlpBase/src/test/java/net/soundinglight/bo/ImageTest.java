/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ImageTest {
	private static final String MIME_TYPE = "image/jpeg";
	private static final byte[] IMAGE_CONTENT = "test content".getBytes();
	private static final float WIDTH = 8.3f;
	private static final float HEIGHT = 13.1f;
	static final Image SAMPLE = new Image(MIME_TYPE, IMAGE_CONTENT, WIDTH, HEIGHT);
	private static final String SERIALIZED_RESOURCE = "../bo/serializedImage.xml";

	@Test
	public void testGetters() {
		ImageTestUtil.assertImage(SAMPLE, MIME_TYPE, IMAGE_CONTENT);
		assertEquals(MIME_TYPE, SAMPLE.getMimeType());
		assertArrayEquals(IMAGE_CONTENT, SAMPLE.getBytes());
		assertEquals(WIDTH, SAMPLE.getWidth(), 0);
		assertEquals(HEIGHT, SAMPLE.getHeight(), 0);
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, SAMPLE);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		ImageTestUtil.assertImageEquals(SAMPLE,
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, Image.class));
	}

	@Test
	public void testToStringShouldYieldDecentTextRepresentation() {
		assertEquals("image/jpeg; size= 8.3*13.1 (w*h in mm); content=dGVz...ZW50",
				SAMPLE.toString());
	}

	@Test
	public void testToStringShouldYieldFullbase64ContentForShortContent() {
		Image image = new Image(MIME_TYPE, "foo".getBytes(), WIDTH, HEIGHT);
		assertEquals("image/jpeg; size= 8.3*13.1 (w*h in mm); content=Zm9v", image.toString());
	}
}
