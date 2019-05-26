/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.input;

import net.soundinglight.poi.bo.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts a {@link HWPFDocument} into a {@link Document}.
 */
public final class HWPFDocumentConverter {
    private static final String FF_CHAR = "\f";
    private int paragraphId;
    private String documentName;

    public HWPFDocumentConverter(String documentName) {
        this.documentName = documentName;
    }

    /**
     * Convert a {@link HWPFDocument} into a {@link Document}.
     *
     * @param document the {@link HWPFDocument} to convert.
     * @return resulting {@link Document}.
     */
    public Document convertDocument(HWPFDocument document) {
        paragraphId = 0;
        List<Paragraph> paragraphs = new ArrayList<>();

        PicturesTable picturesTable = document.getPicturesTable();
        Range range = document.getRange();
        for (int i = 0; i < range.numParagraphs(); i++) {
            org.apache.poi.hwpf.usermodel.Paragraph paragraph = range.getParagraph(i);
            if (FF_CHAR.equals(paragraph.text())) {
                continue;
            }

            paragraphs.add(convertParagraph(paragraph, picturesTable));
        }

        return new Document(documentName, paragraphs);
    }

    private Paragraph convertParagraph(org.apache.poi.hwpf.usermodel.Paragraph paragraph, PicturesTable picturesTable) {
        TextAlignment textAlignment = TextAlignment.byJustificationOfParagraph(paragraph);
        int indentFromLeft = paragraph.getIndentFromLeft();
        int firstLineIndent = paragraph.getIndentFromLeft() + paragraph.getFirstLineIndent();
        return new Paragraph(++paragraphId, textAlignment, indentFromLeft, firstLineIndent,
                convertParagraphElements(paragraph, picturesTable));
    }

    private static List<ParagraphElement> convertParagraphElements(org.apache.poi.hwpf.usermodel.Paragraph paragraph,
																   PicturesTable picturesTable) {
        List<ParagraphElement> elements = new ArrayList<>();

        int count = paragraph.numCharacterRuns();
        for (int i = 0; i < count; i++) {
            org.apache.poi.hwpf.usermodel.CharacterRun run = paragraph.getCharacterRun(i);
            if (picturesTable.hasPicture(run)) {
                org.apache.poi.hwpf.usermodel.Picture pic = picturesTable.extractPicture(run, true);
                elements.add(Picture.fromPoiPicture(pic));
                continue;
            }

            if (picturesTable.hasEscherPicture(run)) {
                continue;
            }

            Font font = Font.byName(run.getFontName());
            String origFontName = (Font.OTHER != font ? null : run.getFontName());
			elements.add(new CharacterRun(run.text(), font, origFontName, run.isBold(), run.isItalic()));
        }

        return elements;
    }
}
