/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.MarshalTestUtil;
import org.junit.Test;

import static net.soundinglight.AssertExt.assertBooleanEquals;
import static net.soundinglight.AssertExt.assertEnumValues;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class TextStyleTest {
	private static final String SERIALIZED_RESOURCE = "../bo/serializedTextStyle.xml";

	@Test
	public void testShouldYieldNormal() {
		assertStyle(TextStyle.NORMAL, false, false);
	}

	@Test
	public void testShouldYieldBold() {
		assertStyle(TextStyle.BOLD, true, false);
	}

	@Test
	public void testShouldYieldItalic() {
		assertStyle(TextStyle.ITALIC, false, true);
	}

	@Test
	public void testShouldYieldBoldItalic() {
		assertStyle(TextStyle.BOLD_ITALIC, true, true);
	}

	@Test
	public void testShouldYieldCSSClassname() {
		assertEquals("bold_italic", TextStyle.BOLD_ITALIC.getCssClass());
	}

	@Test
	public void testEnumValues() throws Exception {
		assertEnumValues(TextStyle.class);
	}

	@Test
	public void testMarshalling() throws Exception {
		MarshalTestUtil.assertMarshalling(SERIALIZED_RESOURCE, TextStyle.BOLD_ITALIC);
	}

	@Test
	public void testUnmarshalling() throws Exception {
		assertEquals(TextStyle.BOLD_ITALIC,
				MarshalTestUtil.unmarshal(SERIALIZED_RESOURCE, TextStyle.class));
	}

	private void assertStyle(TextStyle expected, boolean bold, boolean italic) {
		TextStyle style = TextStyle.getStyle(bold, italic);
		assertSame(expected, style);
		assertBooleanEquals("bold", bold, style.isBold());
		assertBooleanEquals("italic", italic, style.isItalic());
	}
}
