/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.input;

import net.soundinglight.parse.SlpParserSource;
import net.soundinglight.poi.bo.Document;
import net.soundinglight.poi.bo.DocumentCorrections;
import net.soundinglight.poi.bo.Paragraph;
import org.apache.poi.hwpf.HWPFDocument;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link SlpParserSource} that transforms a {@link HWPFDocument} (POI wrapper for a MS Word
 * document) into a {@link Document}.
 */
public class PoiInputAdapter implements SlpParserSource {
    private final HWPFDocument hwpfDocument;
    private String documentName;
    private final DocumentCorrections corrections;

    /**
     * C'tor.
     *
     * @param hwpfDocument the {@link HWPFDocument} to convert into {@link Document}.
     * @param documentName the name of the document.
     * @param corrections  the corrections to apply.
     */
    public PoiInputAdapter(HWPFDocument hwpfDocument, String documentName, DocumentCorrections corrections) {
        this.hwpfDocument = hwpfDocument;
        this.documentName = documentName;
        this.corrections = corrections;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document getDocument() throws PoiInputException {
        Document document = new HWPFDocumentConverter(documentName).convertDocument(hwpfDocument);

        ParagraphElementsSanitizer sanitizer = new ParagraphElementsSanitizer();
        List<Paragraph> sanitized = document.getParagraphs()
                .stream()
                .map(sanitizer::sanitize)
                .collect(Collectors.toList());

        return new Document(document.getName(),
                CharacterReplacer.replaceChars(corrections.apply(sanitized)));
    }
}
