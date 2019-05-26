/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.io;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

/**
 * Input stream based on a fixed string.
 */
public class StringInputStream extends ByteArrayInputStream {
	private String str;

	/**
	 * Constructor.
	 * 
	 * @param str string to base the stream on
	 */
	public StringInputStream(String str) {
		super(str.getBytes(Charset.forName("UTF-8")));
		this.str = str;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return the string that forms the basis of this input stream.
	 */
	@Override
	public String toString() {
		return str;
	}

}
