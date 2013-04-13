package com.nilhcem.mowitnow.core.instruction;

import java.awt.Point;

/**
 * Represents the orientation of a mower in its environment.
 * <p>
 * The orientation is a cardinal point (nord 'N', south 'S', east 'E' or west 'W').
 * </p>
 */
public enum MowerOrientation {
	NORTH('N', 0, 0, 1),
	EAST('E', 90, 1, 0),
	SOUTH('S', 180, 0, -1),
	WEST('W', 270, -1, 0);

	private char letter;
	private int angle;
	private int xIncrement;
	private int yIncrement;

	private MowerOrientation(char letter, int angle, int xIncrement, int yIncrement) {
		this.letter = letter;
		this.angle = angle;
		this.xIncrement = xIncrement;
		this.yIncrement = yIncrement;
	}

	public char getLetter() {
		return letter;
	}

	int getAngle() {
		return angle;
	}

	/**
	 * Returns a new orientation from an initial orientation and some rotation instructions.
	 *
	 * @param initialOrientation the initial orientation before rotating the mower.
	 * @param instruction the rotation instruction.
	 * @return the new mower's orientation, after having rotated.
	 */
	public static MowerOrientation changeOrientation(MowerOrientation initialOrientation, MowerInstruction instruction) {
		return getFromAngle(getProperAngle(initialOrientation.angle + instruction.getAngle()));
	}

	/**
	 * Gets new coordinates if object moves forward in its current orientation.
	 *
	 * @param orientation current orientation
	 * @param coordinates current coordinates
	 * @return
	 */
	public static Point getForwardCoordinates(MowerOrientation orientation, Point coordinates) {
		int x = coordinates.x + orientation.xIncrement;
		int y = coordinates.y + orientation.yIncrement;
		return new Point(x, y);
	}

	static MowerOrientation getFromAngle(int angle) {
		for (MowerOrientation orientation : MowerOrientation.values()) {
			if (orientation.angle == angle) {
				return orientation;
			}
		}
		return null;
	}

	// Check boundaries
	private static int getProperAngle(int angle) {
		return (((angle % 360) + 360) % 360);
	}
}
