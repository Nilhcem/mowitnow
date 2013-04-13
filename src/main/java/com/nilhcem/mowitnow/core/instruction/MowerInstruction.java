package com.nilhcem.mowitnow.core.instruction;

/**
 * Instructions to move the mower.
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
