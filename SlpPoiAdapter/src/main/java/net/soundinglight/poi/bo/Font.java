/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import net.soundinglight.bo.TextType;
import net.soundinglight.poi.CharacterReplacementMap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.List;

/**
 * The font.
 */
@XmlRootElement(name = "font")
@XmlEnum
public enum Font { //

    @XmlEnumValue(value = "Times") TIMES("Times", CharacterReplacementMap.TIMES_REPLACEMENTS, "Times New Roman",
            "Times-Roman"), //
    @XmlEnumValue(value = "S.L. SoulWord") SL_SOULWORD("S.L. SoulWord",
            CharacterReplacementMap.SL_SOULWORD_REPLACEMENTS) {
        /**
         * {@inheritDoc}
         */
        @Override
        public TextType getRelatedTextType() {
            return TextType.SACRED_CAPS;
        }
    }, //
    @XmlEnumValue(value = "S.L. CasWord") SL_CASWORD("S.L. CasWord", CharacterReplacementMap.SL_CASWORD_REPLACEMENTS) {
        /**
         * {@inheritDoc}
         */
        @Override
        public TextType getRelatedTextType() {
            return TextType.SACRED_CAPS;
        }
    }, //
    @XmlEnumValue(value = "S.L. CasLow") SL_CASLOW("S.L. CasLow", CharacterReplacementMap.SL_CASWORD_REPLACEMENTS) {
        /**
         * {@inheritDoc}
         */
        @Override
        public TextType getRelatedTextType() {
            return TextType.SACRED;
        }
    }, //
    @XmlEnumValue(value = "Zapf Dingbats") ZAPF_DINGBATS("Zapf Dingbats",
            CharacterReplacementMap.ZAPF_DINGBATS_REPLACEMENTS), //
    @XmlEnumValue(value = "other") OTHER("Other", CharacterReplacementMap.OTHER_FONT_REPLACEMENTS);

    private String name;
    private final CharacterReplacementMap characterReplacements;
    private List<String> aliases;

    private Font(String name, CharacterReplacementMap characterReplacements, String... aliases) {
        this.name = name;
        this.aliases = Arrays.asList(aliases);
        this.characterReplacements = characterReplacements;
    }

    /**
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the {@link CharacterReplacementMap} for this font.
     */
    public CharacterReplacementMap getCharacterReplacements() {
        return characterReplacements;
    }

    /**
     * @return the {@link TextType} related to this font.
     */
    public TextType getRelatedTextType() {
        return TextType.LATIN;
    }

    /**
     * Get the {@link Font} by its name.
     *
     * @param name the name.
     * @return the font.
     */
    public static Font byName(String name) {
        for (Font font : values()) {
            if (font.getName().equals(name)) {
                return font;
            }
            for (String alias : font.aliases) {
                if (alias.equals(name)) {
                    return font;
                }
            }
        }

        return OTHER;
    }

}
