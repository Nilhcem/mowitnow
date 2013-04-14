package com.nilhcem.mowitnow.core;

import java.awt.Point;
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
	 *
	 * @param width maximum width of the field.
	 * @param height maximum height of the field.
	 * @throws InvalidParameterException when width or height < 1.
	 */
	public Field(int width, int height) {
		if (width < 1) {
			throw new InvalidParameterException("Width should not be < 1");
		}
		if (height < 1) {
			throw new InvalidParameterException("Height should not be < 1");
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
	public boolean isValidLocation(Point location) {
		return (location.x >= 0 && location.x < width &&
				location.y >= 0 && location.y < height);
	}
}
