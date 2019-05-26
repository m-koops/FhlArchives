/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FileTestUtilTest {
    @Test
    public void testShouldYieldTheResourceSourceFile() {
        File src = FileTestUtil.getTestResourceAsFile(FileTestUtil.class, "test.xml");
        assertThat(FileUtil.getNormalizedPath(src),
                endsWith("SlpBase/src/test/resources/net/soundinglight/util/test.xml"));
        assertTrue(src.exists());
    }

    @Test
    public void testShouldYieldNonExistingFileWhenResourceSourceFileCanNotBeFound() {
        File src = FileTestUtil.getTestResourceAsFile(FileTestUtil.class, "unknown.xml");
        assertThat(FileUtil.getNormalizedPath(src),
                endsWith("SlpBase/src/testexp/resources/net/soundinglight/util/unknown.xml"));
        assertFalse(src.exists());
    }

}
