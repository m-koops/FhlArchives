/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import javax.annotation.CheckForNull;

/**
 * Assertion extensions.
 */
public final class AssertExt {
	/**
	 * Assert that a private constructor exists for the given type (and hit it to generate
	 * coverage).
	 * 
	 * @param cls the class instance
	 */
	public static void assertPrivateCtor(Class<?> cls) {
		Constructor<?> ctor = getDefaultCtor(cls);
		assertTrue(cls.getName() + " has a non-private no-arg ctor",
				Modifier.isPrivate(ctor.getModifiers()));
	}

	/**
	 * Assert that no default constructor exists for the given type.
	 * 
	 * @param cls the class instance
	 */
	public static void assertNoDefaultCtor(Class<?> cls) {
		try {
			cls.getDeclaredConstructor(new Class<?>[] {});
			fail("Class '" + cls.getName() + "' is not expected to have a default constructor");
		} catch (NoSuchMethodException e) {
			return;
		}
	}

	private static Constructor<?> getDefaultCtor(Class<?> cls) {
		try {
			Constructor<?> ctor = cls.getDeclaredConstructor(new Class<?>[] {});
			ctor.setAccessible(true);
			// hit the ctor for coverage
			ctor.newInstance(new Object[] {});
			return ctor;
		} catch (NoSuchMethodException e) {
			throw new AssertionError(cls.getName() + " has no private no-arg ctor");
		} catch (InstantiationException e) {
			throw new AssertionError(cls.getName() + " has no private no-arg ctor");
		} catch (IllegalAccessException e) {
			throw new AssertionError(cls.getName() + " has no visible private no-arg ctor");
			// this cannot actually happen since the accessibility has been set to true
		} catch (InvocationTargetException e) {
			throw new AssertionError(cls.getName() + " private ctor throws - why?");
		}
	}

	/**
	 * Assert method values() and getValue() for enumerations.
	 * 
	 * @param <T> the type of enumeration
	 * @param enumClz the enumeration class.
	 * @throws Exception on failures.
	 */
	public static <T> void assertEnumValues(Class<T> enumClz) throws Exception {
		assertTrue(enumClz.isEnum());

		@SuppressWarnings("unchecked")
		T[] values = (T[])(enumClz.getMethod("values").invoke(null));
		assertTrue(values.length > 0);

		T firstValue = values[0];
		org.junit.Assert.assertEquals(firstValue,
				enumClz.getMethod("valueOf", String.class).invoke(null, firstValue.toString()));
	}

	public static void assertBooleanEquals(boolean expected, boolean actual) {
		assertBooleanEquals(null, expected, actual);
	}

	public static void assertBooleanEquals(@CheckForNull String message, boolean expected, boolean actual) {
		String prefix = message == null ? "" : message + ",";
		org.junit.Assert.assertEquals(prefix, Boolean.valueOf(expected), Boolean.valueOf(actual));
	}

	/**
	 * Assert that a string contains another string.
	 * 
	 * @param expected the expected substring
	 * @param actual the actual string
	 */
	public static void assertContains(String expected, String actual) {
		assertContains(null, expected, actual);
	}

	/**
	 * Assert that a string contains another string.
	 * 
	 * @param expected the expected substring
	 * @param actual the actual string
	 * @param msg extra message
	 */
	public static void assertContains(@CheckForNull String msg, String expected, String actual) {
		if (actual == null || !actual.contains(expected)) {
			fail(createPrefixedMessage(msg, "expected to contain: [" + expected + "] but was ["
					+ actual + "]"));
		}
	}

	private static String createPrefixedMessage(@CheckForNull String prefix, String msg) {
		return prefix == null ? msg : prefix + ", " + msg;
	}

	private AssertExt() {
		// prevent instantiation
	}
}
