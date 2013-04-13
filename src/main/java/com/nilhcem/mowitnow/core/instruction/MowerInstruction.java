package com.nilhcem.mowitnow.core.instruction;

/**
 * Instructions to give to the mower.
 * <p>
 * These can be either rotating or moving instructions:
 * <ul>
 * <li>'D' for a right rotation.</li>
 * <li>'G' for a left rotation.</li>
 * <li>'A' to move the mower forward.</li>
 * </ul>
 * </p>
 */
public enum MowerInstruction {
	RIGHT('D', 90),
	LEFT('G', -90),
	FORWARD('A', 0);

	private char letter;
	private int angle;

	private MowerInstruction(char letter, int angle) {
		this.letter = letter;
		this.angle = angle;
	}

	int getAngle() {
		return angle;
	}

	char getLetter() {
		return letter;
	}
}
