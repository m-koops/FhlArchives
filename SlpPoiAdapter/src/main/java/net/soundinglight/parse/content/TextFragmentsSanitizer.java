/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.soundinglight.Pair;
import net.soundinglight.bo.SlpParagraphElement;
import net.soundinglight.bo.TextFragment;
import net.soundinglight.bo.TextType;
import net.soundinglight.parse.strategy.ParserStrategy;

/**
 * Sanitizes {@link TextFragment}s: join fragments when possible and trim leading and trailing
 * whites paces.
 */
public class TextFragmentsSanitizer {
	private static final String WSPACE_OR_PUNCT_CHAR = "\\s\\p{Punct}";

    private static final String SPLIT_GROUP = "(?<wspaceOrPunct>" + "[" + WSPACE_OR_PUNCT_CHAR + "]+" + ")";
	private static final String REMAINDER_GROUP = "(?<remainder>.*?)";
    private static final Pattern SPLIT_WSPACE_OR_PUNCT_FROM_FRONT_PATTERN = Pattern.compile(
            "^" + SPLIT_GROUP + REMAINDER_GROUP);
    private static final Pattern SPLIT_WSPACE_OR_PUNCT_FROM_BACK_PATTERN = Pattern.compile(
            REMAINDER_GROUP + SPLIT_GROUP + "$");

    private static final String TO_CONSOLIDATE_GROUP = "(?<toConsolidate>[^" + WSPACE_OR_PUNCT_CHAR + "]+)";
    private static final Pattern CONSOLIDATE_FROM_PREV_PATTERN = Pattern.compile(
            "^" + REMAINDER_GROUP + TO_CONSOLIDATE_GROUP + "$");
    private static final Pattern CONSOLIDATE_FROM_NEXT_PATTERN = Pattern.compile(
            "^" + TO_CONSOLIDATE_GROUP + REMAINDER_GROUP + "$");

	private static final Pattern STARTS_WITH_WHITESPACE_PATTERN = Pattern.compile("^\\s+");
	private static final Pattern ENDS_WITH_WHITESPACE_PATTERN = Pattern.compile("\\s+$");

	private ParserStrategy strategy;

	TextFragmentsSanitizer(ParserStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Sanitize the given fragments.
	 * 
	 * @param elements the elements to sanitize.
	 * @return the sanitized fragments.
	 */
    List<? extends SlpParagraphElement> sanatize(List<? extends SlpParagraphElement> elements) {
		elements = join(elements);
		elements = stripFromSacred(elements);
		elements = consolidateToSacred(elements);
		elements = join(elements);
		elements = trimFirstAndLastFragment(elements);
		return join(elements);
	}

	List<? extends SlpParagraphElement> join(List<? extends SlpParagraphElement> elements) {
		if (elements.size() <= 1) {
			return elements;
		}

		List<SlpParagraphElement> joined = new ArrayList<>();
		Iterator<? extends SlpParagraphElement> it = elements.iterator();
		SlpParagraphElement previous = it.next();
		while (it.hasNext()) {
			SlpParagraphElement current = it.next();

			if (previous.isTextFragment() && current.isTextFragment()) {
				TextFragment previousFragment = previous.asTextFragment();
				TextFragment currentFragment = current.asTextFragment();

				if (canFragmentsJoin(previousFragment, currentFragment)) {
					previous = TextFragment.join(previousFragment, currentFragment);
					continue;
				}
			}

			joined.add(previous);
			previous = current;
		}
		joined.add(previous);

		return joined;
	}

	private boolean canFragmentsJoin(TextFragment left, TextFragment right) {
		if (left.isBlank() || right.isBlank()) {
			return true;
		}

		if (left.getStyle() != right.getStyle()) {
			return false;
		}

		return left.getType() == right.getType();
	}

	List<? extends SlpParagraphElement> stripFromSacred(List<? extends SlpParagraphElement> elements) {
		List<SlpParagraphElement> result = new ArrayList<>();
		for (SlpParagraphElement element : elements) {
			if (!element.isTextFragment()) {
				result.add(element);
				continue;
			}

			TextFragment textFragment = element.asTextFragment();
			if (textFragment.getType() != TextType.SACRED) {
				result.add(element);
				continue;
			}

			String text = textFragment.getText();
			Matcher matcher = SPLIT_WSPACE_OR_PUNCT_FROM_FRONT_PATTERN.matcher(text);
			if (matcher.matches()) {
				result.add(new TextFragment(matcher.group("wspaceOrPunct")));
				text = matcher.group("remainder");
			}

			matcher = SPLIT_WSPACE_OR_PUNCT_FROM_BACK_PATTERN.matcher(text);
			if (matcher.matches()) {
                result.add(
                        new TextFragment(textFragment.getType(), textFragment.getStyle(), matcher.group("remainder")));
				result.add(new TextFragment(matcher.group("wspaceOrPunct")));
			} else {
				result.add(new TextFragment(textFragment.getType(), textFragment.getStyle(), text));
			}
		}

		return result;
	}

    List<? extends SlpParagraphElement> consolidateToSacred(List<? extends SlpParagraphElement> elements) {
		if (elements.size() <= 1) {
			return elements;
		}

		while (true) {
			List<SlpParagraphElement> result = consolidateSacredIteration(elements);
			if (result.equals(elements)) {
				return result;
			}
			elements = result;
		}
	}

    private List<SlpParagraphElement> consolidateSacredIteration(List<? extends SlpParagraphElement> elements) {
		List<SlpParagraphElement> result = new ArrayList<>(elements);

		for (int index = 0; index < result.size(); index++) {
			SlpParagraphElement current = result.get(index);
			if (!current.isTextFragment()) {
				continue;
			}

			TextFragment currentFragment = current.asTextFragment();
			if (currentFragment.getType() != TextType.SACRED) {
				continue;
			}

			int previousIndex = index - 1;
			if (previousIndex >= 0) {
				SlpParagraphElement previous = result.get(previousIndex);
				if (previous.isTextFragment()) {
					TextFragment previousFragment = previous.asTextFragment();
                    Pair<TextFragment, TextFragment> growResult = consolidateAtFront(previousFragment, currentFragment);
					previousFragment = growResult.getLeft();
					if (previousFragment.isEmpty()) {
						result.remove(previousIndex);
						index--;
					} else {
						result.set(previousIndex, previousFragment);
					}
					currentFragment = growResult.getRight();
				}
			}

			int nextIndex = index + 1;
			if (nextIndex < result.size()) {
				SlpParagraphElement next = result.get(nextIndex);
				if (next.isTextFragment()) {
					TextFragment nextFragment = next.asTextFragment();
                    Pair<TextFragment, TextFragment> growResult = consolidateAtBack(currentFragment, nextFragment);
					nextFragment = growResult.getRight();
					if (nextFragment.isEmpty()) {
						result.remove(nextIndex);
					} else {
						result.set(nextIndex, nextFragment);
					}
					currentFragment = growResult.getLeft();
				}
			}
			result.set(index, currentFragment);
		}
		return result;
	}

    private Pair<TextFragment, TextFragment> consolidateAtFront(TextFragment previous, TextFragment current) {
		if (previous.getType() == TextType.SACRED) {
			return new Pair<>(previous, current);
		}

		Matcher matcher = CONSOLIDATE_FROM_PREV_PATTERN.matcher(previous.getText());
		if (!matcher.matches()) {
			return new Pair<>(previous, current);
		}

        previous = new TextFragment(previous.getType(), previous.getStyle(), matcher.group("remainder"));
        current = new TextFragment(current.getType(), current.getStyle(),
						matcher.group("toConsolidate") + current.getText());
		return new Pair<>(previous, current);
	}

    private Pair<TextFragment, TextFragment> consolidateAtBack(TextFragment current, TextFragment next) {
		if (next.getType() == TextType.SACRED) {
			return new Pair<>(current, next);
		}

		Matcher matcher = CONSOLIDATE_FROM_NEXT_PATTERN.matcher(next.getText());
		if (!matcher.matches()) {
			return new Pair<>(current, next);
		}

		next = new TextFragment(next.getType(), next.getStyle(), matcher.group("remainder"));
        current = new TextFragment(current.getType(), current.getStyle(),
                current.getText() + matcher.group("toConsolidate"));
		return new Pair<>(current, next);
	}

    private List<? extends SlpParagraphElement> trimFirstAndLastFragment(List<? extends SlpParagraphElement> elements) {
		if (elements.isEmpty()) {
			return elements;
		}

		List<SlpParagraphElement> result = new ArrayList<>(elements);
		trimFragment(result, 0, STARTS_WITH_WHITESPACE_PATTERN);

		if (!result.isEmpty()) {
			trimFragment(result, result.size() - 1, ENDS_WITH_WHITESPACE_PATTERN);
		}
		return result;
	}

	private void trimFragment(List<SlpParagraphElement> elements, int index, Pattern pattern) {
		SlpParagraphElement element = elements.get(index);

		if (element.isImage()) {
			return;
		}

		TextFragment fragment = element.asTextFragment();
		String trimmed = pattern.matcher(fragment.getText()).replaceFirst("");
		if (trimmed.isEmpty()) {
			elements.remove(index);
		} else {
			elements.set(index, new TextFragment(fragment.getType(), fragment.getStyle(), trimmed));
		}
	}
}
