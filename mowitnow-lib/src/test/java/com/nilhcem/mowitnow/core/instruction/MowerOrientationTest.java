package com.nilhcem.mowitnow.core.instruction;

import static com.nilhcem.mowitnow.core.instruction.MowerInstruction.*;
import static com.nilhcem.mowitnow.core.instruction.MowerOrientation.*;
import static org.fest.assertions.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.nilhcem.mowitnow.core.Coordinate;

public class MowerOrientationTest {

	@Test
	public void eachOrientationShouldHaveADifferentLetter() {
		List<Character> seen = new ArrayList<Character>();
		for (MowerOrientation orientation : MowerOrientation.values()) {
			if (!seen.isEmpty()) {
				assertThat(orientation.getLetter()).isNotIn(seen);
			}
			seen.add(orientation.getLetter());
		}
	}

	@Test
	public void eachOrientationShouldHaveADifferentAngle() {
		List<Integer> seen = new ArrayList<Integer>();
		for (MowerOrientation orientation : MowerOrientation.values()) {
			if (!seen.isEmpty()) {
				assertThat(orientation.getAngle()).isNotIn(seen);
			}
			seen.add(orientation.getAngle());
		}
	}

	@Test
	public void testOrientationChanges() {
		assertThat(changeOrientation(NORTH, LEFT)).isEqualTo(WEST);
		assertThat(changeOrientation(NORTH, RIGHT)).isEqualTo(EAST);

		assertThat(changeOrientation(EAST, LEFT)).isEqualTo(NORTH);
		assertThat(changeOrientation(EAST, RIGHT)).isEqualTo(SOUTH);

		assertThat(changeOrientation(SOUTH, LEFT)).isEqualTo(EAST);
		assertThat(changeOrientation(SOUTH, RIGHT)).isEqualTo(WEST);

		assertThat(changeOrientation(WEST, LEFT)).isEqualTo(SOUTH);
		assertThat(changeOrientation(WEST, RIGHT)).isEqualTo(NORTH);
	}

	@Test
	public void getFromInvalidAngleShouldReturnNull() {
		assertThat(getFromAngle(42)).isNull();
	}

	@Test
	public void testForwardCoordinates() {
		Coordinate coords = new Coordinate(3, 3);

		assertThat(getForwardCoordinates(NORTH, coords)).isEqualsToByComparingFields(new Coordinate(3, 4));
		assertThat(getForwardCoordinates(SOUTH, coords)).isEqualsToByComparingFields(new Coordinate(3, 2));
		assertThat(getForwardCoordinates(EAST, coords)).isEqualsToByComparingFields(new Coordinate(4, 3));
		assertThat(getForwardCoordinates(WEST, coords)).isEqualsToByComparingFields(new Coordinate(2, 3));
	}

	@Test
	public void testGetOrientationFromChar() {
		assertThat(MowerOrientation.getFromChar('N')).isEqualTo(NORTH);
		assertThat(MowerOrientation.getFromChar('E')).isEqualTo(EAST);
		assertThat(MowerOrientation.getFromChar('S')).isEqualTo(SOUTH);
		assertThat(MowerOrientation.getFromChar('W')).isEqualTo(WEST);
	}

	@Test
	public void getInvalidOrientationShouldReturnNull() {
		assertThat(MowerOrientation.getFromChar('z')).isNull();
	}
}
