/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static org.junit.Assert.*;

import javax.xml.transform.Source;

import org.junit.Test;

public class LocalResourceResolverTest {
	private static final String BASE_PATH =
			"file:/some/path/to/the/project/SlpBase/src/test/resources/net/soundinglight/util/test.xslt";
	private static final LocalResourceResolver RESOLVER = new LocalResourceResolver();

	@Test
	public void testShouldResolveExistingResource() throws Exception {
		Source source = RESOLVER.resolve("../identity.xslt", BASE_PATH);
		assertNotNull(source);
	}

	@Test
	public void testShouldYieldNullForNonExistingResource() throws Exception {
		Source source = RESOLVER.resolve("../non-existing.xslt", BASE_PATH);
		assertNull(source);
	}
}
