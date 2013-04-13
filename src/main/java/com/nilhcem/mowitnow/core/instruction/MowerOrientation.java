package com.nilhcem.mowitnow.core.instruction;

/**
 * Orientation of the mower.
 */
public enum MowerOrientation {
	NORTH('N', 0),
	EAST('E', 90),
	SOUTH('S', 180),
	WEST('W', 270);

	private char letter;
	private int angle;

	private MowerOrientation(char letter, int angle) {
		this.letter = letter;
		this.angle = angle;
	}

	int getAngle() {
		return angle;
	}

	char getLetter() {
		return letter;
	}

	/**
	 * Returns a new orientation from the initial orientation and some rotation instructions.
	 *
	 * @param initialOrientation the initial orientation before rotating the mower.
	 * @param instruction the rotation instruction.
	 * @return the new mower's orientation, after having rotated.
	 */
	public static MowerOrientation changeOrientation(MowerOrientation initialOrientation, MowerInstruction instruction) {
		return getFromAngle(getProperAngle(initialOrientation.angle + instruction.getAngle()));
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
