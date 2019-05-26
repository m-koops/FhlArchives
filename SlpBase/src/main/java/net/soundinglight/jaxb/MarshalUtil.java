/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.jaxb;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.dom.DOMResult;

import org.w3c.dom.Document;

import net.soundinglight.core.CallableT;
import net.soundinglight.core.UnreachableUtil;
import net.soundinglight.util.CloseUtil;
import net.soundinglight.util.IOUtil;

/**
 * JAXB (un)marshal utilities.
 *
 */
public final class MarshalUtil {
	private static final String UTF8 = "UTF-8";

	/**
	 * JAXB marshal an object to a UTF-8 encoded {@link String}.
	 *
	 * @param <T> The type of object to marshal.
	 * @param instance the object to marshal.
	 * @param classes other classes involved in the marshalling.
	 * @return the resulting {@link String}
	 * @throws JAXBException on failure.
	 */
	@SafeVarargs
	public static <T extends Entity> String marshalToString(T instance, Class<? extends Entity>... classes)
			throws JAXBException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshalToStream(instance, baos, classes);
		return IOUtil.toUtf8String(baos);
	}

	/**
	 * JAXB marshal an object to a UTF-8 encoded {@link File}.
	 *
	 * @param <T> The type of object to marshal.
	 * @param instance the object to marshal.
	 * @param file the {@link File} to marshal to.
	 * @param classes other classes involved in the marshalling.
	 * @throws JAXBException on failure.
	 * @throws IOException on failure.
	 */
	@SafeVarargs
	public static <T extends Entity> void marshalToFile(T instance, File file, final Class<? extends Entity>... classes)
			throws JAXBException, IOException {
		marshalToStream(instance, new FileOutputStream(file), classes);
	}

	@SafeVarargs

	/**
	 * JAXB marshal an object to a UTF-8 encoded {@link OutputStream}.
	 *
	 * @param <T> The type of object to marshal.
	 * @param instance the object to marshal.
	 * @param os the {@link OutputStream} to marshal to.
	 * @param classes other classes involved in the marshalling.
	 * @throws JAXBException on failure.
	 * @throws IOException on failure.
	 */
	public static <T extends Entity> void marshalToStream(T instance, final OutputStream os,
			final Class<? extends Entity>... classes) throws JAXBException {
		Writer writer = UnreachableUtil.suppressException(new CallableT<Writer, UnsupportedEncodingException>() {
			@Override
			public Writer call() throws UnsupportedEncodingException {
				return new BufferedWriter(new OutputStreamWriter(os, UTF8));
			}
		});

		try {

			Marshaller marshaller = MarshallerFactory.createMarshaller(joinClasses(instance.getClass(), classes));
			marshaller.marshal(instance, writer);
		} finally {
			CloseUtil.safeClose(writer);
		}
	}

	/**
	 * JAXB marshal an object to a {@link Document}.
	 *
	 * @param <T> The type of object to marshal.
	 * @param instance the object to marshal.
	 * @param classes other classes involved in the marshalling.
	 * @return the resulting {@link String}
	 */
	@SafeVarargs
	public static <T extends Entity> Document marshalToDocument(final T instance,
			final Class<? extends Entity>... classes) {
		return UnreachableUtil.suppressException(new CallableT<Document, JAXBException>() {
			@Override
			public Document call() throws JAXBException {
				@SuppressWarnings("synthetic-access")
				Marshaller marshaller = MarshallerFactory.createMarshaller(joinClasses(instance.getClass(), classes));
				DOMResult result = new DOMResult();
				marshaller.marshal(instance, result);

				return (Document)result.getNode();
			}
		});
	}

	/**
	 * JAXB unmarshal an object from a UTF-8 encoded {@link String}.
	 *
	 * @param <T> The type of object to unmarshal from the {@link String}.
	 * @param serialized the {@link String} to unmarshal.
	 * @param clz The class of object to unmarshal
	 * @param classes other classes involved in the unmarshalling.
	 * @return the resulting object.
	 * @throws JAXBException on failure.
	 */
	@SafeVarargs
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T unmarshalFromString(final String serialized, Class<T> clz,
			Class<? extends Entity>... classes) throws JAXBException {

		CallableT<ByteArrayInputStream, UnsupportedEncodingException> callable =
				new CallableT<ByteArrayInputStream, UnsupportedEncodingException>() {
					@Override
					public ByteArrayInputStream call() throws UnsupportedEncodingException {
						return new ByteArrayInputStream(serialized.getBytes(UTF8));
					}
				};
		return (T)MarshallerFactory.createUnmarshaller(joinClasses(clz, classes)).unmarshal(
				UnreachableUtil.suppressException(callable));
	}

	/**
	 * JAXB unmarshal an object from a UTF-8 encoded {@link String}.
	 *
	 * @param <T> The type of object to unmarshal from the {@link String}.
	 * @param file the {@link File} to unmarshal from.
	 * @param clz The class of object to unmarshal
	 * @param classes other classes involved in the unmarshalling.
	 * @return the resulting object.
	 * @throws JAXBException on failure.
	 * @throws IOException on failure.
	 */
	@SafeVarargs
	public static <T extends Entity> T unmarshalFromFile(File file, Class<T> clz, Class<? extends Entity>... classes)
			throws JAXBException, IOException {
		return unmarshalFromStream(new FileInputStream(file), clz, classes);
	}

	/**
	 * JAXB unmarshal an object from a UTF-8 encoded {@link String}.
	 *
	 * @param <T> The type of object to unmarshal from the {@link String}.
	 * @param is the {@link InputStream} to unmarshal from.
	 * @param clz The class of object to unmarshal
	 * @param classes other classes involved in the unmarshalling.
	 * @return the resulting object.
	 * @throws JAXBException on failure.
	 * @throws UnsupportedEncodingException on failure.
	 */
	@SafeVarargs
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T unmarshalFromStream(InputStream is, Class<T> clz,
			Class<? extends Entity>... classes) throws JAXBException, UnsupportedEncodingException {
		Reader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, UTF8));
			return (T)MarshallerFactory.createUnmarshaller(joinClasses(clz, classes)).unmarshal(reader);
		} finally {
			CloseUtil.safeClose(reader);
		}
	}

	@SafeVarargs
	private static <T extends Entity> Class<?>[] joinClasses(Class<T> clz, Class<? extends Entity>... classes) {
		List<Class<?>> joinedClasses = new ArrayList<>();
		joinedClasses.add(clz);
		joinedClasses.addAll(Arrays.<Class<? extends Entity>>asList(classes));
		return joinedClasses.toArray(new Class<?>[joinedClasses.size()]);
	}

	private MarshalUtil() {
		// prevent instantiation
	}
}
