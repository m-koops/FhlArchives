/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import static net.soundinglight.AssertExt.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TextAlignmentTest {
	@Test
	public void testEnumValues() throws Exception {
		assertEnumValues(TextAlignment.class);
	}

	@Test
	public void testByJustification() {
		assertEquals(TextAlignment.LEFT,
				TextAlignment.byJustification(TextAlignment.POI_JUSTIFICATION_LEFT));
		assertEquals(TextAlignment.CENTER,
				TextAlignment.byJustification(TextAlignment.POI_JUSTIFICATION_CENTER));
		assertEquals(TextAlignment.RIGHT,
				TextAlignment.byJustification(TextAlignment.POI_JUSTIFICATION_RIGHT));
		assertEquals(TextAlignment.JUSTIFY,
				TextAlignment.byJustification(TextAlignment.POI_JUSTIFICATION_JUSTIFY));
		assertEquals(TextAlignment.LEFT, TextAlignment.byJustification(-1));
	}
}
