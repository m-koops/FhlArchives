/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import net.soundinglight.jaxb.Entity;
import net.soundinglight.util.StringUtil;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A paragraph from a POI document.
 */
@XmlRootElement(name = "paragraph")
@XmlType(propOrder = {"id", "alignment", "indentFromLeft", "firstLineIndent", "elements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Paragraph implements Entity {
    @XmlAttribute(required = true)
    private final int id;
    @XmlAttribute(required = true)
    private final TextAlignment alignment;
    @XmlAttribute(required = true)
    private final int indentFromLeft;
    @XmlAttribute(required = true)
    private final int firstLineIndent;
    @XmlElementRefs({@XmlElementRef(name = "characterRun", type = CharacterRun.class, required = false),
            @XmlElementRef(name = "picture", type = Picture.class, required = false)})
    private final List<ParagraphElement> elements = new ArrayList<>();

    /**
     * C'tor.
     *
     * @param id              the unique Id of the paragraph with the POI document.
     * @param identFromLeft   the indentation from the left margin.
     * @param firstLineIndent the indentation of the first line.
     * @param elements        the elements (character runs and pictures) contained in the paragraph.
     */
    public Paragraph(int id, int identFromLeft, int firstLineIndent, ParagraphElement... elements) {
        this(id, TextAlignment.JUSTIFY, identFromLeft, firstLineIndent, elements);
    }

    /**
     * C'tor.
     *
     * @param id              the unique Id of the paragraph with the POI document.
     * @param alignment       the {@link TextAlignment} to use.
     * @param identFromLeft   the indentation from the left margin.
     * @param firstLineIndent the indentation of the first line.
     * @param elements        the elements (character runs and pictures) contained in the paragraph.
     */
    public Paragraph(int id, TextAlignment alignment, int identFromLeft, int firstLineIndent,
                     ParagraphElement... elements) {
        this(id, alignment, identFromLeft, firstLineIndent, Arrays.asList(elements));
    }

    /**
     * C'tor.
     *
     * @param id              the unique Id of the paragraph with the POI document.
     * @param alignment       the {@link TextAlignment} to use.
     * @param indentFromLeft  the indentation from the left margin.
     * @param firstLineIndent the indentation of the first line.
     * @param elements        the elements (character runs and pictures) contained in the paragraph.
     */
    public Paragraph(int id, TextAlignment alignment, int indentFromLeft, int firstLineIndent, List<?
            extends ParagraphElement> elements) {
        this.id = id;
        this.alignment = alignment;
        this.indentFromLeft = indentFromLeft;
        this.firstLineIndent = firstLineIndent;
        this.elements.addAll(elements);
    }

    /**
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * @return the text alignment.
     */
    public TextAlignment getTextAlignment() {
        return alignment;
    }

    /**
     * @return the base indentation from left.
     */
    public int getIndentFromLeft() {
        return indentFromLeft;
    }

    /**
     * @return the indentation from left for the first line.
     */
    public int getFirstLineIndent() {
        return firstLineIndent;
    }

    /**
     * @return the elements (character runs and pictures) contained in the paragraph.
     */
    public List<ParagraphElement> getElements() {
        return elements;
    }

    /**
     * @return the text of the paragraph.
     */
    public String getText() {
        StringBuilder builder = new StringBuilder();

        for (ParagraphElement element : elements) {
            if (element.isCharacterRun()) {
                builder.append(element.asCharacterRun().getText());
            }
        }

        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringUtil.asStringList(elements).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Paragraph)) {
            return false;
        }

        Paragraph paragraph = (Paragraph) other;
        return getId() == paragraph.getId() && //
                getIndentFromLeft() == paragraph.getIndentFromLeft() && //
                getFirstLineIndent() == paragraph.getFirstLineIndent() && //
                alignment == paragraph.alignment && //
                Objects.equals(getElements(), paragraph.getElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), alignment, getIndentFromLeft(), getFirstLineIndent(), getElements());
    }

    @SuppressWarnings("unused")
    private Paragraph() {
        // for JAXB
        this(0, 0, 0);
    }
}
