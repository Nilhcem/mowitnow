package com.nilhcem.mowitnow.core.instruction;

import static org.fest.assertions.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MowerInstructionTest {

	@Test
	public void forwardShouldNotModifyMowerOrentation() {
		assertThat(MowerInstruction.FORWARD.getAngle()).isEqualTo(0);
	}

	@Test
	public void leftIsTheOppositeOfRight() {
		int leftAngle = MowerInstruction.LEFT.getAngle();
		int rightAngle = MowerInstruction.RIGHT.getAngle();

		assertThat(leftAngle).isNotEqualTo(rightAngle);
		assertThat(Math.abs(leftAngle)).isEqualTo(Math.abs(rightAngle));
	}

	@Test
	public void eachInstructionShouldHaveADifferentLetter() {
		List<Character> seen = new ArrayList<Character>();
		for (MowerInstruction instr : MowerInstruction.values()) {
			if (!seen.isEmpty()) {
				assertThat(instr.getLetter()).isNotIn(seen);
			}
			seen.add(instr.getLetter());
		}
	}

	@Test
	public void testGetInstructionsFromChar() {
		assertThat(MowerInstruction.getFromChar('D')).isEqualTo(MowerInstruction.RIGHT);
		assertThat(MowerInstruction.getFromChar('G')).isEqualTo(MowerInstruction.LEFT);
		assertThat(MowerInstruction.getFromChar('A')).isEqualTo(MowerInstruction.FORWARD);
	}

	@Test
	public void getInvalidInstructionShouldReturnNull() {
		assertThat(MowerInstruction.getFromChar('z')).isNull();
	}
}
