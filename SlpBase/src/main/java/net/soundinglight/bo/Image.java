/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.bo;

import org.apache.commons.codec.binary.Base64;

import javax.xml.bind.annotation.*;
import java.nio.charset.Charset;

/**
 * An embedded image.
 */
@XmlRootElement(name = "image")
@XmlType(propOrder = { "mimeType" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Image extends SlpParagraphElement {
	private static final int SEGMENT_LENGTH = 4;
	private static final int MAX_LENGTH_FULL_DISPLAY = 2 * SEGMENT_LENGTH;
	private static final Charset UTF8 = Charset.forName("UTF-8");

	@XmlAttribute(required = true)
	private final String mimeType;
	@XmlAttribute(name = "widthMm", required = true)
	private final float width;
	@XmlAttribute(name = "heightMm", required = true)
	private final float height;
	@XmlValue
	private final String base64Content;

	/**
	 * C'tor.
	 *
	 * @param mimeType the mime type of the image.
	 * @param content the binary content of the image.
	 * @param width the width in mm.
	 * @param height the height in mm.
	 */
	public Image(String mimeType, byte[] content, float width, float height) {
		this.mimeType = mimeType;
		this.width = width;
		this.height = height;
		base64Content = new String(Base64.encodeBase64(content), UTF8);
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
	 * @return the width.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @return the height.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return mimeType + "; size= " + width + "*" + height + " (w*h in mm); content=" + getSummarizedBase64Content();
	}

	private String getSummarizedBase64Content() {
		int length = base64Content.length();
		if (length <= MAX_LENGTH_FULL_DISPLAY) {
			return base64Content;
		}

		return base64Content.substring(0, SEGMENT_LENGTH) + "..."
				+ base64Content.substring(length - SEGMENT_LENGTH, length);
	}

	@SuppressWarnings("unused")
	private Image() {
		// for JAXB
		this(null, new byte[0], 0, 0);
	}
}
