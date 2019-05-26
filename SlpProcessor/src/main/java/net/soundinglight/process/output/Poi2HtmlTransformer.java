/*
 * (c) 2015 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.process.output;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.annotation.CheckForNull;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import net.soundinglight.core.CallableT;
import net.soundinglight.core.UnreachableUtil;
import net.soundinglight.jaxb.Entity;
import net.soundinglight.jaxb.MarshalUtil;
import net.soundinglight.util.CloseUtil;
import net.soundinglight.util.IOUtil;
import net.soundinglight.util.XmlUtil;

public class Poi2HtmlTransformer {
	private final URL xslResource;

	public Poi2HtmlTransformer(URL xslResource) {
		this.xslResource = xslResource;
	}

	public <T extends Entity> void transformToStream(final T entity, final OutputStream os) {
		UnreachableUtil.suppressException(new CallableT<Void, TransformerException>() {
			@Override
			public Void call() throws TransformerException {
				@SuppressWarnings("synthetic-access")
				StreamSource xslStreamSource = createXslSource();

				try {
					XmlUtil.transform(new DOMSource(MarshalUtil.marshalToDocument(entity)),
							new StreamResult(os), xslStreamSource, true);
				} finally {
					CloseUtil.safeClose(xslStreamSource.getInputStream());
				}
				return null;
			}
		});
	}

	private StreamSource createXslSource() {
		StreamSource source = new StreamSource(openStream(xslResource));
		source.setSystemId(xslResource.toExternalForm());
		return source;
	}

	@CheckForNull
	private InputStream openStream(@CheckForNull URL resource) {
		return new BufferedInputStream(IOUtil.openStream(resource));
	}

}
