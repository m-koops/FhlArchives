/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.*;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A character run from a POI document.
 */
@XmlRootElement(name = "characterRun")
@XmlType(propOrder = {"font", "bold", "italic", "text"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CharacterRun extends ParagraphElement {
    private static final Pattern WHITESPACE_ONLY_RUN_PATTERN = Pattern.compile("^\\s*$");

    @XmlAttribute(required = true)
    private Font font;
    @CheckForNull
    @XmlAttribute
    private String origFontName;
    @XmlAttribute(required = true)
    private boolean bold;
    @XmlAttribute(required = true)
    private boolean italic;
    @XmlValue
    private String text;

    /**
     * C'tor.
     *  @param text   the text of the run
     * @param font   the {@link Font} used.
     * @param origFontName
     * @param bold   whether the text is bold.
     * @param italic whether the text is italic.
     */
    public CharacterRun(String text, Font font, @CheckForNull String origFontName, boolean bold, boolean italic) {
        this.text = text;
        this.font = font;
        this.origFontName = origFontName;
        this.bold = bold;
        this.italic = italic;
    }

    /**
     * @return the text.
     */
    public String getText() {
        return text;
    }

    /**
     * @return the font.
     */
    public Font getFont() {
        return font;
    }

    /**
     * @return <code>true</code> when bold text.
     */
    public boolean isBold() {
        return bold;
    }

    /**
     * @return <code>true</code> when italic text.
     */
    public boolean isItalic() {
        return italic;
    }

    /**
     * @return <code>true</code> when text empty or only whitespace characters.
     */
    public boolean isBlank() {
        return WHITESPACE_ONLY_RUN_PATTERN.matcher(text).matches();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getText();
    }

    /**
     * Join two {@link CharacterRun}s.
     * <p>
     * The the characteristics (font, bold, italic) to use for the resulting joined run is
     * determined by the content of the originating runs:
     * <ul>
     * <li>when the first run contains significant (non-whitespace) content, the resulting run takes
     * the characteristics of the first run;</li>
     * <li>when only the second run contains significant content, the resulting run takes the
     * characteristics of the that run;</li>
     * <li>when neither run contains significant content, the resulting run takes the
     * characteristics of the first run;</li>
     * </ul>
     *
     * @param left  the first run to join.
     * @param right the second run to join.
     * @return the joined runs.
     */
    public static CharacterRun join(CharacterRun left, CharacterRun right) {
        String joined = left.getText() + right.getText();

        CharacterRun characteristics;
        if (left.isBlank()) {
            characteristics = right.isBlank() ? left : right;
        } else {
            characteristics = left;
        }

        return new CharacterRun(joined, characteristics.getFont(), null, characteristics.isBold(), characteristics.italic);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof CharacterRun)) {
            return false;
        }

        CharacterRun that = (CharacterRun) other;
        return isBold() == that.isBold() && //
                isItalic() == that.isItalic() && //
                getFont() == that.getFont() && //
                Objects.equals(getText(), that.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFont(), isBold(), isItalic(), getText());
    }

    @SuppressWarnings("unused")
    private CharacterRun() {
        // for JAXB
    }
}
