package com.nilhcem.mowitnow.core;

import java.security.InvalidParameterException;

/**
 * Environment (garden) that contains mowers.
 * <p>It may also contain grass, flowers, trees, moles... but we won't be too picky</p>
 */
public final class Field {
	private final int width;
	private final int height;

	/**
	 * Creates a new field, specifying its dimensions.
	 * <p>
	 * A dimension of (w=1, h=1) will create a field containing 4 elements (we start counting from 0).
	 * </p>
	 *
	 * @param width maximum width of the field.
	 * @param height maximum height of the field.
	 * @throws InvalidParameterException when width or height < 1.
	 */
	public Field(int width, int height) {
		if (width < 0) {
			throw new InvalidParameterException("Width should not be < 0");
		}
		if (height < 0) {
			throw new InvalidParameterException("Height should not be < 0");
		}

		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	/**
	 * Indicates whether coordinates are valid.
	 *
	 * @param coordinate the coordinate to check.
	 * @return true if coordinate is in field, or false if outside field boundaries.
	 */
	public boolean isValidLocation(Coordinate coordinate) {
		return (coordinate.getX() >= 0 && coordinate.getX() <= width &&
				coordinate.getY() >= 0 && coordinate.getY() <= height);
	}
}
