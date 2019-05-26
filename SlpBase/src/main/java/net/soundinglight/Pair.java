/*
 * (c) 2014 Soundinglight Publishing
 * All rights reserved.
 */
package net.soundinglight;


/**
 * A pair of two things.
 * 
 * @param <L> the type of the left-hand argument
 * @param <R> the type of the right-hand argument
 */
public final class Pair<L, R> {
	private final L left;
	private final R right;

	/**
	 * C'tor.
	 * 
	 * @param left the left-hand argument
	 * @param right the right-hand argument
	 */
	public Pair(L left, R right) {
		assert left != null;
		assert right != null;

		this.left = left;
		this.right = right;
	}

	/**
	 * @return the left.
	 */
	public L getLeft() {
		return left;
	}

	/**
	 * @return the right.
	 */
	public R getRight() {
		return right;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "(" + left + "," + right + ")";
	}
}
