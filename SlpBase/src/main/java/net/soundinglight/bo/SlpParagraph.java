/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.JAXBConstants;
import net.soundinglight.util.StringUtil;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An SLP paragraph.
 */
@XmlRootElement(name = "paragraph", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlType(propOrder = {"id", "paragraphLabel", "bulletLabel", "alignment", "indent", "elements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class SlpParagraph implements Entity {
    @XmlAttribute(required = true)
    private final int id;
    @XmlAttribute(required = true)
    @SuppressWarnings("unused")
    private final Alignment alignment;
    @XmlAttribute(required = true)
    private final boolean indent;
    @XmlAttribute
    @CheckForNull
    private final String paragraphLabel;
    @XmlAttribute
    @CheckForNull
    private final String bulletLabel;
    @XmlElementRefs({@XmlElementRef(name = "fragment", type = TextFragment.class, required = false),
            @XmlElementRef(name = "image", type = Image.class, required = false)})
    private final List<? extends SlpParagraphElement> elements;

    /**
     * C'tor.
     *
     * @param id             the unique Id of the SLP paragraph within the document.
     * @param elements       the {@link SlpParagraphElement}s that make up the paragraph.
     * @param alignment      the alignment of the paragraph.
     * @param indent         whether the {@link SlpParagraph} is indented.
     * @param paragraphLabel the label of the paragraph.
     * @param bulletLabel    the bullet item label attached to the paragraph.
     */
    public SlpParagraph(int id, List<? extends SlpParagraphElement> elements, Alignment alignment, boolean indent,
                        @CheckForNull String paragraphLabel, @CheckForNull String bulletLabel) {
        this.id = id;
        this.elements = elements;
        this.alignment = alignment;
        this.indent = indent;
        this.paragraphLabel = paragraphLabel;
        this.bulletLabel = bulletLabel;
    }

    /**
     * @return the id.
     */
    public int getId() {
        return id;
    }

    /**
     * @return the elements.
     */
    public List<? extends SlpParagraphElement> getElements() {
        return elements;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    /**
     * @return whether paragraph has an indent.
     */
    public boolean hasIndent() {
        return indent;
    }

    /**
     * @return the paragraph label.
     */
    @CheckForNull
    public String getParagraphLabel() {
        return paragraphLabel;
    }

    /**
     * @return the bullet item label.
     */
    @CheckForNull
    public String getBulletLabel() {
        return bulletLabel;
    }

    /**
     * @return <code>true</code> when the paragraph is empty (no label and none or all-empty
     * fragments, no images)
     */
    public boolean isEmpty() {
        if (!StringUtil.isNullOrEmpty(paragraphLabel) || !StringUtil.isNullOrEmpty(bulletLabel)) {
            return false;
        }

        for (SlpParagraphElement element : elements) {
            if (element.isImage()) {
                return false;
            }

            if (element.asTextFragment().hasSignificantContent()) {
                return false;
            }
        }

        return true;
    }

    /**
     * @return the paragraph as plain text.
     */
    public String asPlainText() {
        StringBuilder builder = new StringBuilder();

        for (SlpParagraphElement element : elements) {
            if (element.isTextFragment()) {
                builder.append(element.asTextFragment().getText());
            }
        }

        return builder.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "id=" + id + "; '" + paragraphLabel + "'; " + indent + "; " + StringUtil.asStringList(elements);
    }

    @SuppressWarnings("unused")
    private SlpParagraph() {
        // for JAXB
        this(0, new ArrayList<SlpParagraphElement>(), Alignment.LEFT, false, null, null);
    }
}
