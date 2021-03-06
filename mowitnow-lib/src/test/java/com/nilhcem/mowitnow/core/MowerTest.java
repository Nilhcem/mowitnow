package com.nilhcem.mowitnow.core;

import org.junit.*;

import com.nilhcem.mowitnow.core.instruction.MowerInstruction;
import com.nilhcem.mowitnow.core.instruction.MowerOrientation;

import static org.fest.assertions.api.Assertions.*;

public class MowerTest {
	private Field field;

	@Before
	public void setUp() {
		field = new Field(1, 1);
	}

	@Test
	public void testMowerPositionStringRepresentation() {
		Field field = new Field(10, 10);
		Mower mower = new Mower(new Coordinate(3, 4), MowerOrientation.SOUTH, field);
		assertThat(mower.getPosition()).isEqualTo("3 4 S");
	}

	@Test
	public void testInvalidForwardInstructions() {
		assertThat(new Mower(new Coordinate(0, 0), MowerOrientation.WEST, field).process(MowerInstruction.FORWARD)).isFalse();
		assertThat(new Mower(new Coordinate(0, 0), MowerOrientation.SOUTH, field).process(MowerInstruction.FORWARD)).isFalse();
		assertThat(new Mower(new Coordinate(0, 1), MowerOrientation.NORTH, field).process(MowerInstruction.FORWARD)).isFalse();
		assertThat(new Mower(new Coordinate(1, 0), MowerOrientation.EAST, field).process(MowerInstruction.FORWARD)).isFalse();
	}

	@Test
	public void testMultipleForwardInstructions() {
		Mower mower = new Mower(new Coordinate(0, 0), MowerOrientation.NORTH, field);

		assertThat(mower.getPosition()).isEqualTo("0 0 N");
		assertThat(mower.process(MowerInstruction.FORWARD)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("0 1 N");
		assertThat(mower.process(MowerInstruction.RIGHT)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("0 1 E");
		assertThat(mower.process(MowerInstruction.FORWARD)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("1 1 E");
		assertThat(mower.process(MowerInstruction.LEFT)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("1 1 N");
		assertThat(mower.process(MowerInstruction.LEFT)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("1 1 W");
		assertThat(mower.process(MowerInstruction.LEFT)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("1 1 S");
		assertThat(mower.process(MowerInstruction.FORWARD)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("1 0 S");
		assertThat(mower.process(MowerInstruction.RIGHT)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("1 0 W");
		assertThat(mower.process(MowerInstruction.FORWARD)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("0 0 W");
		assertThat(mower.process(MowerInstruction.RIGHT)).isTrue();
		assertThat(mower.getPosition()).isEqualTo("0 0 N");
	}
}
