/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import static net.soundinglight.AssertExt.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import net.soundinglight.LogStore;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

public class CloseUtilTest {
	@Rule
	public final LogStore store = new LogStore(Level.INFO);

	@Test
	public void testPrivateCtor() {
		assertPrivateCtor(CloseUtil.class);
	}

	@Test
	public void testSafeCloseInputStreamOk() {
		InputStream is = new ByteArrayInputStream(new byte[0]);
		CloseUtil.safeClose(is);
	}

	@Test
	public void testSafeCloseInputStreamShouldLogOnFailure() throws Exception {
		InputStream is = Mockito.mock(InputStream.class);
		doThrow(new IOException("deliberate")).when(is).close();

		store.expectMsg(Level.SEVERE, "failed to close input stream");

		CloseUtil.safeClose(is);
	}

	@Test
	public void testSafeCloseNullInputStream() {
		CloseUtil.safeClose((InputStream)null);
	}

	@Test
	public void testSafeCloseNullStreamSource() {
		CloseUtil.safeClose((Source)null);
	}

	@Test
	public void testSafeCloseStreamSource() {
		InputStream is = Mockito.mock(InputStream.class);
		CloseUtil.safeClose(new StreamSource(is));
	}

	@Test
	public void testSafeCloseStreamSourceShouldLogOnFailure() throws Exception {
		InputStream is = Mockito.mock(InputStream.class);
		doThrow(new IOException("deliberate")).when(is).close();

		store.expectMsg(Level.SEVERE, "failed to close input stream");

		CloseUtil.safeClose(new StreamSource(is));
	}

	@Test
	public void testSafeCloseNullCloseable() {
		CloseUtil.safeClose((Closeable)null);
	}

	@Test
	public void testSafeCloseCloseable() {
		Closeable closeable = Mockito.mock(Closeable.class);
		CloseUtil.safeClose(closeable);
	}

	@Test
	public void testSafeCloseCloseableShouldLogOnFailure() throws Exception {
		Closeable closeable = Mockito.mock(Closeable.class);
		doThrow(new IOException("deliberate")).when(closeable).close();

		store.expectMsg(Level.SEVERE, "failed to close");

		CloseUtil.safeClose(closeable);
	}

}
