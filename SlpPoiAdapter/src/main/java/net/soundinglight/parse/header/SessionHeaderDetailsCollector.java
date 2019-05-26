/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.parse.header;

import net.soundinglight.bo.MomentOfDay;
import net.soundinglight.bo.Recommendation;
import net.soundinglight.bo.SessionDetails;
import net.soundinglight.bo.media.GenericVideoDetails;
import net.soundinglight.bo.media.MediaDetails;
import net.soundinglight.bo.media.NtscVideoDetails;
import net.soundinglight.bo.media.PalVideoDetails;
import net.soundinglight.core.CallableT;
import net.soundinglight.core.UnreachableUtil;
import net.soundinglight.parse.ParseException;
import net.soundinglight.util.CollectionUtil;

import javax.annotation.CheckForNull;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Collects the session header details during the parsing of the header.
 */
public class SessionHeaderDetailsCollector {
    private static final Pattern GROUP_NAMES_PATTERN = Pattern.compile("\\(\\?<([a-zA-Z][a-zA-Z0-9]*)>");
    private static final List<String> MONTHS = Arrays.asList("january", "february", "march", "april", "may", "june",
            "july", "august", "september", "october", "november", "december");
    private static final List<String> MONTH_ABBRS = Arrays.asList("jan", "feb", "mar", "apr", "may", "jun", "jul",
            "aug", "sept", "oct", "nov", "dec");
    private final Map<String, List<String>> details = new Hashtable<>();
    private final int tapelistYear;

    /**
     * C'tor.
     *
     * @param year the year of the parsed tapelist.
     */
    public SessionHeaderDetailsCollector(int year) {
        tapelistYear = year;
    }

    /**
     * Add the given value to an existing detail determined by {@code key}, or creates a new detail
     * for {@code key} with given value.
     *
     * @param key   the key of the detail.
     * @param value the value to add.
     */
    public void addValue(String key, String value) {
        String trimmed = value.trim();

        List<String> existing = getValues(key);
        if (existing == null) {
            details.put(key, CollectionUtil.asModifiableList(trimmed));
        } else {
            existing.add(trimmed);
        }
    }

    /**
     * @param key the key of the detail.
     * @return the single value for the detail determined by {@code key} or null if no detail exists
     * for the {@code key}.
     * @throws ParseException when the detail is not single valued.
     */
    @CheckForNull
    public String getSingleValue(String key) throws ParseException {
        List<String> values = getValues(key);
        if (values == null) {
            return null;
        }

        if (values.size() > 1) {
            throw new ParseException("only a single value expected for detail '" + key + "', but found " + values);
        }

        return values.get(0);
    }

    /**
     * @param key the key of the detail.
     * @return the integer value for the detail determined by {@code key} or null if no detail
     * exists for the {@code key}.
     * @throws ParseException when the detail is not single valued.
     */
    @CheckForNull
    public Integer safeGetIntegerValue(String key) throws ParseException {
        String value = getSingleValue(key);
        return value == null ? null : Integer.valueOf(value);
    }

    /**
     * @param key the key of the detail.
     * @return all values for the detail determined by {@code key} or null if no detail exists for
     * the {@code key}.
     */
    @CheckForNull
    public List<String> getValues(String key) {
        return details.get(key);
    }

    /**
     * @return the session date normalized to format "mm/dd/yyyy".
     * @throws ParseException on failure.
     */
    @CheckForNull
    public String extractNormalizedSessionDate() throws ParseException {
        Integer dateValue = safeGetIntegerValue("date");
        String monthText = getSingleValue("month");
        if (monthText == null) {
            return null;
        }
        int monthValue = determine1BasedMonthValue(monthText);
        String yearValue = getSingleValue("year");
        if (yearValue == null) {
            yearValue = String.valueOf(tapelistYear);
        }

        return String.format("%02d/%02d/%s", Integer.valueOf(monthValue), dateValue, yearValue);
    }

    static int determine1BasedMonthValue(String monthText) throws ParseException {
        String toLower = monthText.toLowerCase(Locale.ROOT);
        if (toLower.endsWith(".")) {
            toLower = toLower.substring(0, toLower.length() - 1);
        }

        int monthValue = MONTHS.indexOf(toLower);
        if (monthValue == -1) {
            monthValue = MONTH_ABBRS.indexOf(toLower);
        }

        if (monthValue == -1) {
            throw new ParseException("'" + monthText + "' cannot be parsed as a name or abbreviation of a month");
        }

        return monthValue + 1; // 1 based
    }

    /**
     * @return the details for PAL and NTSC media.
     */
    public List<MediaDetails> extractMediaDetails() {
        List<MediaDetails> result = new ArrayList<>();

        result.addAll(extractMediaDetails("pal", PalVideoDetails.class));
        result.addAll(extractMediaDetails("ntsc", NtscVideoDetails.class));
        result.addAll(extractMediaDetails("video", GenericVideoDetails.class));

        return result;
    }

    private List<MediaDetails> extractMediaDetails(String mediaType, Class<? extends MediaDetails> clz) {
        List<String> ids = getValues(mediaType);
        if (ids == null) {
            return Collections.emptyList();
        }

        List<MediaDetails> result = new ArrayList<>();
        List<String> restrictions = getValues(mediaType + "Restriction");
        if (restrictions == null) {
            restrictions = Collections.emptyList();
        }

        for (String id : ids) {
            String restriction = restrictions.isEmpty() ? "" : restrictions.remove(0);
            result.add(createMediaDetails(id, restriction, clz));
        }

        return result;
    }

    private static MediaDetails createMediaDetails(final String id, final String restriction, final Class<? extends
            MediaDetails> clz) {
        return UnreachableUtil.suppressException(new CallableT<MediaDetails, Throwable>() {

            @Override
            public MediaDetails call() throws Throwable {
                Constructor<?> ctor = clz.getDeclaredConstructor(String.class, String.class);
                return (MediaDetails) ctor.newInstance(new Object[]{id, restriction});
            }
        });
    }

    /**
     * Collects details from the given matcher and stores those details in the collector.
     *
     * @param matcher the matcher to collect details from.
     */
    public void collectDetailsFromMatcher(Matcher matcher) {
        Matcher groupnamesMatcher = GROUP_NAMES_PATTERN.matcher(matcher.pattern().pattern());
        while (groupnamesMatcher.find()) {
            String groupName = groupnamesMatcher.group(1);
            String value = matcher.group(groupName);

            if (value != null) {
                addValue(groupName, value);
            }
        }
    }

    /**
     * Create {@link SessionDetails} from the collector's content.
     *
     * @return the created {@link SessionDetails} instance.
     * @throws ParseException on failure to parse a collected value to a valid value within the
     *                        {@link SessionDetails} instance.
     */
    public SessionDetails createSessionDetails() throws ParseException {
        String sessionId = getSingleValue("id");
        if (sessionId == null) {
            throw new ParseException("failed to determine session id");
        }
        sessionId = sessionId.replace("&", "+");

        return SessionDetails.builder(sessionId)
                .setAvailable(true)
                .setTitle(getSingleValue("title"))
                .setSubTitle(getSingleValue("subTitle"))
                .setRestriction(getSingleValue("restriction"))
                .setSubRestrictions(getValues("subRestriction"))
                .setDate(extractNormalizedSessionDate())
                .setMomentOfDay(MomentOfDay.fromString(getSingleValue("momentOfDay")))
                .setLocation(getSingleValue("location"))
                .setDuration(safeGetIntegerValue("duration"))
                .setDurationSideA(safeGetIntegerValue("durationSideA"))
                .setDurationSideB(safeGetIntegerValue("durationSideB"))
                .setRecommendation(Recommendation.fromString(getSingleValue("recommendation")))
                .setKeyTopics(safeSplit("keyTopics"))
                .setAdditionalRemarks(getValues("additionalRemarks"))
                .setAuthor(getSingleValue("author"))
                .setMediaDetails(extractMediaDetails())
                .setNotes(getValues("notes"))
                .build();
    }

    @CheckForNull
    private List<String> safeSplit(String key) throws ParseException {
        String value = getSingleValue(key);
        return value == null ? null : Arrays.asList(value.split("; +"));
    }

}
