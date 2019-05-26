/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */

package net.soundinglight.bo;

import net.soundinglight.bo.media.GenericVideoDetails;
import net.soundinglight.bo.media.MediaDetails;
import net.soundinglight.bo.media.NtscVideoDetails;
import net.soundinglight.bo.media.PalVideoDetails;
import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.JAXBConstants;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Session details.
 */
@XmlRootElement(name = "sessionDetails", namespace = JAXBConstants.XML_NAMESPACE_TAPELIST)
@XmlType(propOrder = {"id", "available", "location", "date", "momentOfDay", "duration", "durationSideA",
		"durationSideB", "recommendation", "title", "subTitle", "restriction", "subRestrictions", "keyTopics",
		"additionalRemarks", "author", "mediaDetails", "notes"})
@XmlAccessorType(XmlAccessType.FIELD)
public final class SessionDetails implements Entity {
    @XmlAttribute(required = true)
    private final String id;
    @XmlAttribute(required = true)
    private final boolean available;
    @XmlElement
    @CheckForNull
    private final String date;
    @XmlElement
    @CheckForNull
    private final MomentOfDay momentOfDay;
    @XmlElement
    @CheckForNull
    private final Integer duration;
    @XmlElement
    @CheckForNull
    private final Integer durationSideA;
    @XmlElement
    @CheckForNull
    private final Integer durationSideB;
    @XmlElement
    @CheckForNull
    private final Recommendation recommendation;
    @XmlElement
    @CheckForNull
    private String title;
    @XmlElement
    @CheckForNull
    private final String subTitle;
    @XmlElement
    @CheckForNull
    private final String restriction;
    @XmlElementWrapper
    @XmlElement(name = "subRestriction")
    @CheckForNull
    private final List<String> subRestrictions;
    @XmlElementWrapper
    @XmlElement(name = "keyTopic")
    @CheckForNull
    private final List<String> keyTopics;
    @XmlElement
    @CheckForNull
    private final String location;
    @XmlElementWrapper
    @XmlElement(name = "additionalRemark")
    @CheckForNull
    private final List<String> additionalRemarks;
    @XmlElement
    @CheckForNull
    private final String author;
    @XmlElementWrapper
    @XmlElementRefs({@XmlElementRef(name = "genericVideoDetails", type = GenericVideoDetails.class, required = false)
			, @XmlElementRef(name = "ntscVideoDetails", type = NtscVideoDetails.class, required = false),
			@XmlElementRef(name = "palVideoDetails", type = PalVideoDetails.class, required = false)})
    private final List<MediaDetails> mediaDetails;
    @XmlElementWrapper
    @XmlElement(name = "note")
    @CheckForNull
    private final List<String> notes;

    public SessionDetails(Builder builder) {
        this.id = builder.id;
        this.available = builder.available;
        this.title = builder.title;
        this.subTitle = builder.subTitle;
        this.restriction = builder.restriction;
        this.subRestrictions = builder.subRestrictions;
        this.date = builder.date;
        this.momentOfDay = builder.momentOfDay;
        this.location = builder.location;
        this.duration = builder.duration;
        this.durationSideA = builder.durationSideA;
        this.durationSideB = builder.durationSideB;
        this.recommendation = builder.recommendation;
        this.keyTopics = builder.keyTopics;
        this.additionalRemarks = builder.additionalRemarks;
        this.author = builder.author;
        this.mediaDetails = builder.mediaDetails;
        this.notes = builder.notes;
    }

    /**
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return <code>true</code> when session is available.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @return the restriction.
     */
    @CheckForNull
    public String getRestriction() {
        return restriction;
    }

    /**
     * @return the sub-restrictions.
     */
    @CheckForNull
    public List<String> getSubRestrictions() {
        return subRestrictions;
    }

    /**
     * @return the date.
     */
    @CheckForNull
    public String getDate() {
        return date;
    }

    /**
     * @return the timeOfDay.
     */
    public MomentOfDay getMomentOfDay() {
        return momentOfDay;
    }

    /**
     * @return the location.
     */
    @CheckForNull
    public String getLocation() {
        return location;
    }

    /**
     * @return the duration.
     */
    public int getDuration() {
        return duration == null ? -1 : duration.intValue();
    }

    /**
     * @return the durationSideA.
     */
    public int getDurationSideA() {
        return durationSideA == null ? -1 : durationSideA.intValue();
    }

    /**
     * @return the durationSideA.
     */
    public int getDurationSideB() {
        return durationSideB == null ? -1 : durationSideB.intValue();
    }

    /**
     * @return the recommendation.
     */
    @CheckForNull
    public Recommendation getRecommendation() {
        return recommendation;
    }

    /**
     * @return the keyTopics.
     */
    @CheckForNull
    public List<String> getKeyTopics() {
        return keyTopics;
    }

    /**
     * @return the title.
     */
    @CheckForNull
    public String getTitle() {
        return title;
    }

    /**
     * @return the subTitle.
     */
    @CheckForNull
    public String getSubTitle() {
        return subTitle;
    }

    /**
     * @return the additionalRemarks.
     */
    @CheckForNull
    public List<String> getAdditionalRemarks() {
        return additionalRemarks;
    }

    @CheckForNull
    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(String.valueOf(id));
        builder.append(": ");

        if (!available) {
            builder.append("is not available");
        } else {
            builder.append(getDate());
            builder.append("; ");
            builder.append(getMomentOfDay());
            builder.append("; ");
            builder.append(getTitle());
        }

        return builder.toString();
    }

    private SessionDetails() {
        // for JAXB
        this(builder(null));
    }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public static final class Builder {
        @CheckForNull
        private final String id;
        private boolean available;
        @CheckForNull
        private String title;
        @CheckForNull
        private String subTitle;
        @CheckForNull
        private String restriction;
        @CheckForNull
        private List<String> subRestrictions;
        @CheckForNull
        private String date;
        private MomentOfDay momentOfDay;
        @CheckForNull
        private String location;
        @CheckForNull
        private Integer duration;
        @CheckForNull
        private Integer durationSideA;
        @CheckForNull
        private Integer durationSideB;
        private Recommendation recommendation;
        @CheckForNull
        private List<String> keyTopics;
        @CheckForNull
        private List<String> additionalRemarks;
        @CheckForNull
        private String author;
        @CheckForNull
        private List<MediaDetails> mediaDetails;
        @CheckForNull
        private List<String> notes;

        private Builder(@CheckForNull String id) {
            this.id = id;
        }

        public SessionDetails build() {
            return new SessionDetails(this);
        }

        public Builder setAvailable(boolean available) {
            this.available = available;
            return this;
        }

        public Builder setTitle(@CheckForNull String title) {
            this.title = title;
            return this;
        }

        public Builder setSubTitle(@CheckForNull String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder setRestriction(@CheckForNull String restriction) {
            this.restriction = restriction;
            return this;
        }

        public Builder setSubRestrictions(@CheckForNull List<String> subRestrictions) {
            this.subRestrictions = wrapCollection(subRestrictions);
            return this;
        }

        public Builder setDate(@CheckForNull String date) {
            this.date = date;
            return this;
        }

        public Builder setMomentOfDay(MomentOfDay momentOfDay) {
            this.momentOfDay = momentOfDay;
            return this;
        }

        public Builder setLocation(@CheckForNull String location) {
            this.location = location;
            return this;
        }

        public Builder setDuration(@CheckForNull Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder setDurationSideA(@CheckForNull Integer durationSideA) {
            this.durationSideA = durationSideA;
            return this;
        }

        public Builder setDurationSideB(@CheckForNull Integer durationSideB) {
            this.durationSideB = durationSideB;
            return this;
        }

        public Builder setRecommendation(Recommendation recommendation) {
            this.recommendation = recommendation;
            return this;
        }

        public Builder setKeyTopics(@CheckForNull List<String> keyTopics) {
            this.keyTopics = wrapCollection(keyTopics);
            return this;
        }

        public Builder setAdditionalRemarks(@CheckForNull List<String> additionalRemarks) {
            this.additionalRemarks = wrapCollection(additionalRemarks);
            return this;
        }

        public Builder setAuthor(@CheckForNull String author) {
            this.author = author;
            return this;
        }

        public Builder setMediaDetails(@CheckForNull List<MediaDetails> mediaDetails) {
            this.mediaDetails = wrapCollection(mediaDetails);
            return this;
        }

        public Builder setNotes(@CheckForNull List<String> notes) {
            this.notes = wrapCollection(notes);
            return this;
        }

        @CheckForNull
        private <T> ArrayList<T> wrapCollection(@CheckForNull List<T> values) {
            return values == null ? null : new ArrayList<>(values);
        }
    }
}
