/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

/**
 * Standard JUnit 4 test runner that runs the tests in sorted order.
 */
public class SortedBlockJUnit4ClassRunner extends BlockJUnit4ClassRunner {
	/**
	 * Creates a SortedBlockJUnit4ClassRunner to run {@code clz}.
	 * 
	 * @param clz the test class to run the tests for.
	 * @throws InitializationError if the test class is malformed.
	 */
	public SortedBlockJUnit4ClassRunner(Class<?> clz) throws InitializationError {
		super(clz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<FrameworkMethod> getChildren() {
		List<FrameworkMethod> children = new ArrayList<>(super.getChildren());
		Collections.sort(children, new Comparator<FrameworkMethod>() {
			@Override
			public int compare(FrameworkMethod left, FrameworkMethod right) {
				return left.getName().compareTo(right.getName());
			}
		});

		return children;
	}
}
