/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight.util;

import net.soundinglight.core.CallableT;
import net.soundinglight.core.FatalProgrammingException;
import net.soundinglight.core.UnreachableUtil;

import javax.annotation.CheckForNull;
import javax.annotation.WillClose;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * IO related utility methods.
 */
public final class IOUtil {
    private static final String UTF8 = "UTF-8";
    public static final int BUF_SIZE = 8192;

    /**
     * Open a stream from given URL.
     *
     * @param url the URL to open stream from.
     * @return the opened stream, or <code>null</code> when stream can't be opened.
     */
    @CheckForNull
    public static InputStream openStream(@CheckForNull URL url) {
        try {
            return url == null ? null : url.openStream();
        } catch (IOException e) {
            return null;
        }
    }

    @CheckForNull
    public static InputStream openStream(@CheckForNull File file) {
        try {
            return file == null ? null : new BufferedInputStream(new FileInputStream(file));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * copy a resource to a given {@link File}.
     *
     * @param resource    the {@link URL} to the resource.
     * @param destination the {@link File} to copy to.
     * @throws IOException on failures.
     */
    public static void copyResource(URL resource, File destination) throws IOException {
        assert !destination.toString().contains("resources");

        InputStream is = null;
        FileOutputStream os = null;
        try {
            createDir(destination.getParentFile());
            is = resource.openStream();
            os = new FileOutputStream(destination);
            copyStream(is, os);
        } finally {
            CloseUtil.safeClose(is);
            CloseUtil.safeClose(os);
        }
    }

    /**
     * Create a directory, recursively.
     *
     * @param dir the directory to create.
     */
    public static void createDir(@CheckForNull File dir) {
        if (dir != null) {
            // CHECKSTYLE:OFF:RegexpSingleline
            dir.mkdirs();
            // CHECKSTYLE:ON:RegexpSingleline
        }
    }

    /**
     * Delete files or directory (recursive).
     *
     * @param file the file or directory to delete.
     * @throws IOException on failure to delete the file or directory.
     */
    public static void delete(File file) throws IOException {
        if (file.isDirectory()) {
            for (File c : file.listFiles()) {
                delete(c);
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete file: " + file);
        }
    }

    /**
     * Copy an {@link InputStream} to an {@link OutputStream}.
     *
     * @param is the {@link InputStream} to copy.
     * @param os the target {@link OutputStream}.
     * @return the number of bytes copied.
     * @throws IOException on failures.
     */
    public static long copyStream(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[BUF_SIZE];
        long totalBytesRead = 0;
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
            totalBytesRead += bytesRead;
        }
        return totalBytesRead;
    }

    /**
     * Read the specified {@link InputStream} and return the read content as {@link String}.
     *
     * @param is the {@link InputStream} to read.
     * @return the read content.
     * @throws IOException on failure to read or close the stream.
     */
    public static String readStreamAsString(@WillClose InputStream is) throws IOException {
        InputStream bis = new BufferedInputStream(is);
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            copyStream(bis, os);
            return os.toString("UTF-8");
        } finally {
            CloseUtil.safeClose(bis);
        }
    }

    /**
     * Write the provided {@link String} to the specified {@link OutputStream}.
     *
     * @param os the {@link OutputStream} to write to.
     * @throws IOException on failure to write or close the stream.
     */
    public static void writeStringToStream(@WillClose OutputStream os, String content) throws IOException {
        Writer out = null;
        try {
            out = new OutputStreamWriter(new BufferedOutputStream(os), "UTF-8");
            out.write(content);
        } finally {
            CloseUtil.safeClose(out);
        }
    }

    /**
     * Get a resource as {@link File}.
     *
     * @param clz      the class to load the resource with.
     * @param resource the resource to load.
     * @return the {@link File} pointing to the resource.
     */
    public static File getResourceAsFile(Class<?> clz, String resource) {
        return fileUrlAsFile(clz.getResource(resource));
    }

    /**
     * Read a resource as {@link String}.
     *
     * @param clz      the class to load the resource with.
     * @param resource the resource to read.
     * @return the {@link String} content of the resource.
     * @throws IOException on failure.
     */
    public static String readResourceAsString(Class<?> clz, String resource) throws IOException {
        InputStream is = clz.getResourceAsStream(resource);
        if (is == null) {
            throw new IOException("resource '" + resource + "' does not exist");
        }
        return StringUtil.normalizeNewLine(readStreamAsString(is));
    }

    /**
     * Convert a {@link URL} that starts with "file:/" to a {@link File}.
     *
     * @param url the {@link URL}.
     * @return the {@link File}.
     */
    public static File fileUrlAsFile(URL url) {
        if (!url.toExternalForm().startsWith("file:/")) {
            throw new FatalProgrammingException(
                    "method can only be used for file urls: url='" + url.toExternalForm() + "");
        }

        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            return new File(url.getPath());
        }
    }

    /**
     * @param baos the {@link ByteArrayOutputStream} to extract the {@link String} of.
     * @return the extracted {@link String}.
     */
    public static String toUtf8String(final ByteArrayOutputStream baos) {
        return UnreachableUtil.suppressException(new CallableT<String, UnsupportedEncodingException>() {
            @Override
            public String call() throws UnsupportedEncodingException {
                return baos.toString(UTF8);
            }
        });
    }

    /**
     * Normalize path separator.
     *
     * @param path the path to normalize.
     * @return the normalized path.
     */
    public static String normalizePathSeparator(String path) {
        return path.replaceAll("\\\\", "/");
    }

    private IOUtil() {
        // prevent instantiation
    }
}
