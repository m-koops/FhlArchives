/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.input;

import net.soundinglight.poi.bo.CharacterRun;
import net.soundinglight.poi.bo.Paragraph;
import net.soundinglight.poi.bo.ParagraphElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Sanitizes {@link ParagraphElement}s: join runs when possible and trim trailing white spaces.
 */
public class ParagraphElementsSanitizer {
    private static final Pattern ENDS_WITH_WHITESPACE_PATTERN = Pattern.compile("\\s+$");

    public Paragraph sanitize(Paragraph paragraph) {
        return new Paragraph(paragraph.getId(), paragraph.getTextAlignment(), paragraph.getIndentFromLeft(),
                paragraph.getFirstLineIndent(), sanitize(paragraph.getElements()));
    }

    /**
     * Sanitize the given {@link ParagraphElement}s.
     *
     * @param elements the elements to sanitize.
     * @return the sanitized elements.
     */
    public List<? extends ParagraphElement> sanitize(List<? extends ParagraphElement> elements) {
        elements = join(elements);
        return trimLastElement(elements);
    }

    /**
     * Join the given fragments where possible.
     *
     * @param elements the elements to join.
     * @return the joined fragments.
     */
    public List<? extends ParagraphElement> join(List<? extends ParagraphElement> elements) {
        if (elements.size() <= 1) {
            return elements;
        }

        List<ParagraphElement> joined = new ArrayList<>();
        Iterator<? extends ParagraphElement> it = elements.iterator();
        ParagraphElement previous = it.next();
        while (it.hasNext()) {
            ParagraphElement current = it.next();

            if (previous.isCharacterRun() && current.isCharacterRun()) {
                CharacterRun previousRun = previous.asCharacterRun();
                CharacterRun currentRun = current.asCharacterRun();

                if (canRunsJoin(previousRun, currentRun)) {
                    previous = CharacterRun.join(previousRun, currentRun);
                    continue;
                }
            }

            joined.add(previous);
            previous = current;
        }
        joined.add(previous);

        return joined;
    }

    private boolean canRunsJoin(CharacterRun left, CharacterRun right) {
        if (left.isBlank() || right.isBlank()) {
            return true;
        }

        if (left.getFont() != right.getFont()) {
            return false;
        }

        if (left.isBold() != right.isBold()) {
            return false;
        }

        return left.isItalic() == right.isItalic();
    }

    private List<? extends ParagraphElement> trimLastElement(List<? extends ParagraphElement> elements) {
        if (elements.isEmpty()) {
            return elements;
        }
        int index = elements.size() - 1;
        ParagraphElement element = elements.get(index);

        if (element.isPicture()) {
            return elements;
        }

        List<ParagraphElement> result = new ArrayList<>(elements);

        CharacterRun run = element.asCharacterRun();
        String trimmed = ENDS_WITH_WHITESPACE_PATTERN.matcher(run.getText()).replaceFirst("");
        if (trimmed.isEmpty()) {
            result.remove(index);
        } else {
            result.set(index,
                    new CharacterRun(trimmed, run.getFont(), null, run.isBold(), run.isItalic()));
        }

        return result;
    }
}
