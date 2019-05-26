/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.poi.bo;

import net.soundinglight.bo.Image;
import org.apache.commons.codec.binary.Base64;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * A picture nested in the document.
 */
@XmlRootElement(name = "picture")
@XmlType(propOrder = {"mimeType", "horizontalScalingFactor", "verticalScalingFactor", "dxaGoal", "dyaGoal"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Picture extends ParagraphElement {
    private static final int PRECISION = 100;
    private static final int MM_PER_CM = 10;
    private static final double CM_PER_INCH = 2.54;
    private static final int TWIPS_PER_INCH = 1440;
    private static final int SCALE = 3;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final int BASE64_TOSTRING_SAMPLE_SIZE = 10;
    @XmlAttribute(required = true)
    private final String mimeType;
    @XmlAttribute(required = true)
    private final float horizontalScalingFactor;
    @XmlAttribute(required = true)
    private final float verticalScalingFactor;
    @XmlAttribute(name = "dxaGoalMm", required = true)
    private final int dxaGoal;
    @XmlAttribute(name = "dyaGoalMm", required = true)
    private final int dyaGoal;
    @XmlValue
    private final String base64Content;

    /**
     * Transforms a {@link org.apache.poi.hwpf.usermodel.Picture} into a {@link Picture}.
     *
     * @param picture the {@link org.apache.poi.hwpf.usermodel.Picture} to transform.
     * @return resulting {@link Picture}.
     */
    public static Picture fromPoiPicture(org.apache.poi.hwpf.usermodel.Picture picture) {
        String mime = picture.suggestPictureType().getMime();
        byte[] content = picture.getContent();
        int horizontalScalingFactor = picture.getHorizontalScalingFactor();
        int verticalScalingFactor = picture.getVerticalScalingFactor();
        int dxaGoal = picture.getDxaGoal();
        int dyaGoal = picture.getDyaGoal();
        return new Picture(mime, content, toFraction(horizontalScalingFactor), toFraction(verticalScalingFactor),
                twipsToMm(dxaGoal), twipsToMm(dyaGoal));
    }

    private static int twipsToMm(int twips) {
        float inch = (float) twips / TWIPS_PER_INCH;
        return (int) Math.round(inch * CM_PER_INCH * MM_PER_CM);
    }

    private static float toFraction(int factor) {
        return new BigDecimal(factor).movePointLeft(SCALE).floatValue();
    }

    /**
     * C'tor.
     *
     * @param mimeType                the mime type of the picture.
     * @param content                 the binary content of the picture.
     * @param horizontalScalingFactor the horizontal scaling factor.
     * @param verticalScalingFactor   the vertical scaling factor.
     * @param dxaGoal                 the Dxa goal in mm.
     * @param dyaGoal                 the Dya goal in mm.
     */
    public Picture(String mimeType, byte[] content, float horizontalScalingFactor, float verticalScalingFactor,
                   int dxaGoal, int dyaGoal) {
        this.mimeType = mimeType;
        base64Content = new String(Base64.encodeBase64(content), UTF8);
        this.horizontalScalingFactor = horizontalScalingFactor;
        this.verticalScalingFactor = verticalScalingFactor;
        this.dxaGoal = dxaGoal;
        this.dyaGoal = dyaGoal;
    }

    /**
     * @return the mimeType.
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @return the base64Content.
     */
    public byte[] getBytes() {
        return Base64.decodeBase64(base64Content.getBytes(UTF8));
    }

    /**
     * @return the horizontalScalingFactor.
     */
    public float getHorizontalScalingFactor() {
        return horizontalScalingFactor;
    }

    /**
     * @return the verticalScalingFactor.
     */
    public float getVerticalScalingFactor() {
        return verticalScalingFactor;
    }

    /**
     * @return the dxaGoal.
     */
    public int getDxaGoal() {
        return dxaGoal;
    }

    /**
     * @return the dyaGoal.
     */
    public int getDyaGoal() {
        return dyaGoal;
    }

    /**
     * Transforms the picture into an {@link Image}.
     *
     * @return resulting {@link Image}.
     */
    public Image asImage() {
        float width = round(getDxaGoal() * getHorizontalScalingFactor());
        float height = round(getDyaGoal() * getVerticalScalingFactor());
        return new Image(getMimeType(), getBytes(), width, height);
    }

    private static float round(double value) {
        return Math.round(value * PRECISION) / PRECISION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "mime: " + mimeType //
                + "; hor. scale fact: " + horizontalScalingFactor //
                + "; vert. scale factor: " + verticalScalingFactor //
                + "; DxaGoalMm: " + dxaGoal //
                + "; DyaGoalMm: " + dyaGoal //
                + "; base64Content: '" //
                + base64Content.substring(0, BASE64_TOSTRING_SAMPLE_SIZE) + "...'";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Picture)) {
            return false;
        }

        Picture picture = (Picture) other;
        return Float.compare(picture.getHorizontalScalingFactor(), getHorizontalScalingFactor()) == 0 && //
                Float.compare(picture.getVerticalScalingFactor(), getVerticalScalingFactor()) == 0 && //
                getDxaGoal() == picture.getDxaGoal() && //
                getDyaGoal() == picture.getDyaGoal() && //
                Objects.equals(getMimeType(), picture.getMimeType()) && //
                Objects.equals(base64Content, picture.base64Content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMimeType(), getHorizontalScalingFactor(), getVerticalScalingFactor(), getDxaGoal(),
                getDyaGoal(), base64Content);
    }

    @SuppressWarnings("unused")
    private Picture() {
        // for JAXB
        this("", new byte[0], 0f, 0f, -1, -1);
    }
}
