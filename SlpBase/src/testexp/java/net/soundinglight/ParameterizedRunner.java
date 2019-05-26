/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Extension to JUnit's Parameterized runner, which supports named tests.
 */
public class ParameterizedRunner extends Suite {
	/**
	 * Annotation for a method which provides parameters to be injected into the test class
	 * constructor by {@link ParameterizedRunner}.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public static @interface Parameters {
		// empty
	}

	public static class ParameterSet {
		private final String name;
		private final Object[] parameters;

		public ParameterSet(String name, Object... parameters) {
			this.name = name;
			this.parameters = parameters;
			assert !name.contains("(") : "parentheses break the Eclipse displaying: " + name;
			assert !name.contains(")") : "parentheses break the Eclipse displaying: " + name;
		}

		public String getName() {
			return name;
		}

		public Object[] getParameters() {
			return parameters;
		}
	}

	public static class TestClassRunnerForParameters extends SortedBlockJUnit4ClassRunner {
		private final ParameterSet parameterSet;

		public TestClassRunnerForParameters(Class<?> type, ParameterSet parameterSet)
				throws InitializationError {
			super(type);
			this.parameterSet = parameterSet;
		}

		@Override
		public Object createTest() throws Exception {
			return getTestClass().getOnlyConstructor().newInstance(parameterSet.getParameters());
		}

		@Override
		protected String getName() {
			return parameterSet.getName();
		}

		@Override
		protected String testName(final FrameworkMethod method) {
			return String.format("%s[%s]", method.getName(), parameterSet.getName());
		}

		@Override
		protected void validateConstructor(List<Throwable> errors) {
			validateOnlyOneConstructor(errors);
		}

		@Override
		protected Statement classBlock(RunNotifier notifier) {
			return childrenInvoker(notifier);
		}
	}

	private final ArrayList<Runner> runners = new ArrayList<>();

	/**
	 * Only called reflectively. Do not use programmatically.
	 * 
	 * @param klass the test {@link Class} being run.
	 * @throws Throwable on failure.
	 */
	public ParameterizedRunner(Class<?> klass) throws Throwable {
		super(klass, Collections.<Runner>emptyList());
		List<ParameterSet> parametersList = getParametersList(getTestClass());
		for (int i = 0; i < parametersList.size(); i++) {
			runners.add(new TestClassRunnerForParameters(getTestClass().getJavaClass(),
					parametersList.get(i)));
		}
	}

	@Override
	protected List<Runner> getChildren() {
		return runners;
	}

	@SuppressWarnings("unchecked")
	private List<ParameterSet> getParametersList(TestClass klass) throws Throwable {
		return (List<ParameterSet>)getParametersMethod(klass).invokeExplosively(null);
	}

	private FrameworkMethod getParametersMethod(TestClass testClass) throws Exception {
		List<FrameworkMethod> methods = testClass.getAnnotatedMethods(Parameters.class);
		for (FrameworkMethod each : methods) {
			int modifiers = each.getMethod().getModifiers();
			if (Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers)) {
				return each;
			}
		}

		throw new Exception("No public static method annotated with " + Parameters.class.getName()
				+ " on class " + testClass.getName());
	}
}
