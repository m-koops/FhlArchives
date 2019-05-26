/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import net.soundinglight.SlpConstants;
import net.soundinglight.core.CallableT;
import net.soundinglight.core.UnreachableUtil;
import net.soundinglight.io.DirectByteArrayOutputStream;

/**
 * Resolves {@link java.net.URI}s local resources with help of the class loader.
 */
public class LocalResourceResolver implements URIResolver {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Source resolve(String href, String base) throws TransformerException {
		String path = resolveLocalResourcePath(href, base);
		final InputStream is = LocalResourceResolver.class.getResourceAsStream(path);
		if (is == null) {
			return null;
		}

		return UnreachableUtil.suppressException(new CallableT<Source, IOException>() {
			@Override
			public Source call() throws IOException {
				try {
					return createInMemorySource();
				} finally {
					CloseUtil.safeClose(is);
				}
			}

			private Source createInMemorySource() throws IOException {
				DirectByteArrayOutputStream dbaos = new DirectByteArrayOutputStream();
				IOUtil.copyStream(is, dbaos);
				return new StreamSource(dbaos.getInputStream());
			}
		});
	}

	private String resolveLocalResourcePath(String href, String base) {
		String[] parts = base.split(SlpConstants.BASE_PACKAGE_PATH);
		assert parts.length > 1 : "base xsl file is not in the " + SlpConstants.BASE_PACKAGE_NAME
				+ " package";
		String resourcePath = SlpConstants.BASE_PACKAGE_PATH + parts[1];

		File resourcePackagePath = new File(resourcePath).getParentFile();
		String resource = new File(resourcePackagePath, href).getPath();

		return IOUtil.normalizePathSeparator(resource);
	}
}
