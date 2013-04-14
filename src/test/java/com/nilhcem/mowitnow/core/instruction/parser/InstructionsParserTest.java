package com.nilhcem.mowitnow.core.instruction.parser;

import static org.fest.assertions.api.Assertions.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.nilhcem.mowitnow.core.Field;
import com.nilhcem.mowitnow.core.Mower;
import com.nilhcem.mowitnow.core.instruction.MowerInstruction;
import com.nilhcem.mowitnow.core.instruction.MowerOrientation;

public class InstructionsParserTest {
	private List<String> instructions;

	@Before
	public void setUp() {
		instructions = new ArrayList<String>();
		instructions.add("5 7");
		instructions.add("1 2 N");
		instructions.add("GAGAGAGAA");
	}

	@Test
	public void instanciatingWithLessThan3InstructionsShouldThrow() {
		InstructionsParser parser = new InstructionsParser(instructions);

		Field field = parser.getField();
		assertThat(field.getWidth()).isEqualTo(5);
		assertThat(field.getHeight()).isEqualTo(7);
	}

	@Test(expected = ParserException.class)
	public void invalidFieldInstructionsShouldThrow() {
		instructions.remove(0);
		instructions.add(0, "4 2 0");
		new InstructionsParser(instructions);
	}

	@Test(expected = ParserException.class)
	public void invalidMowerLocationShouldThrow() {
		instructions.remove(1);
		instructions.add(1, "42");
		new InstructionsParser(instructions);
	}

	@Test(expected = ParserException.class)
	public void invalidMowerInstructionsOnlyShouldThrow() {
		instructions.remove(2);
		instructions.add("WTF");
		new InstructionsParser(instructions);
	}

	@Test
	public void testOneMowerValidData() {
		InstructionsParser parser = new InstructionsParser(instructions);

		// Testing field
		Field field = parser.getField();
		assertThat(field).isNotNull();
		assertThat(field.getWidth()).isEqualTo(5);
		assertThat(field.getHeight()).isEqualTo(7);

		// Testing mowers
		Mower mower = parser.getNextMower();
		assertThat(mower).isNotNull();
		assertThat(parser.getNextMower()).isNull();

		// Testing instructions
		List<MowerInstruction> instructions = parser.getInstructionsForMower(mower);
		assertThat(instructions.size()).isEqualTo(9);
		Mower unknown = new Mower(new Point(0, 0), MowerOrientation.EAST, null);
		assertThat(parser.getInstructionsForMower(unknown)).isNull();
	}

	@Test
	public void invalidMowerInstructionsShouldBeIgnored() {
		instructions.remove(2);
		instructions.add("OMG WTF BBQ!!!1"); // only valid instruction here is 'G'
		InstructionsParser parser = new InstructionsParser(instructions);
		Mower mower = parser.getNextMower();
		List<MowerInstruction> instructions = parser.getInstructionsForMower(mower);
		assertThat(instructions.size()).isEqualTo(1);
		assertThat(instructions.get(0)).isEqualsToByComparingFields(MowerInstruction.LEFT);
	}

	@Test
	public void testMultipleMowersValidData() {
		List<String> instrs = new ArrayList<String>();
		instrs.add("  4  	3 	");
		instrs.add("  1   	2  	N ");
		instrs.add(" 	G DG DGD  GD !D");
		instrs.add("1 1 E");
		instrs.add("A");
		InstructionsParser parser = new InstructionsParser(instrs);

		// Testing field
		Field field = parser.getField();
		assertThat(field).isNotNull();
		assertThat(field.getWidth()).isEqualTo(4);
		assertThat(field.getHeight()).isEqualTo(3);

		// Testing mowers
		Mower mower1 = parser.getNextMower();
		Mower mower2 = parser.getNextMower();
		Mower mower3 = parser.getNextMower();
		assertThat(mower1).isNotNull();
		assertThat(mower2).isNotNull();
		assertThat(mower3).isNull();

		// Testing instructions
		assertThat(parser.getInstructionsForMower(mower1).size()).isEqualTo(9);
		assertThat(parser.getInstructionsForMower(mower2).size()).isEqualTo(1);
	}
}