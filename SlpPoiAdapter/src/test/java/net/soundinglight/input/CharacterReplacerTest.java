/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.input;

import net.soundinglight.jaxb.MarshalTestUtil;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.poi.bo.DocumentTestUtil;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.poi.bo.ParagraphTestUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static net.soundinglight.AssertExt.assertPrivateCtor;
import static org.junit.Assert.assertTrue;

public class CharacterReplacerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testPrivateCtor() {
        assertPrivateCtor(CharacterReplacer.class);
    }

    @Test
    public void testReplaceCharactersInMarshallableDocument() throws Exception {
        Document raw = MarshalTestUtil.unmarshal("/net/soundinglight/sample.poi.raw.nocharsreplaced.xml",
                Document.class);
        List<Paragraph> actual = CharacterReplacer.replaceChars(raw.getParagraphs());
        List<Paragraph> expected = MarshalTestUtil.unmarshal("/net/soundinglight/sample.poi.raw.xml", Document.class)
                .getParagraphs();
        ParagraphTestUtil.assertParagraphsEquals(expected, actual);
    }

    @Test
    public void testReplaceCharactersShouldHandlePictureGracefully() {
        Document doc = DocumentTestUtil.createTestDocument(1);
        List<Paragraph> paragraphs = doc.getParagraphs();
        assertTrue(paragraphs.get(0).getElements().stream().anyMatch(e -> e.isPicture()));
        CharacterReplacer.replaceChars(paragraphs);
    }
}
