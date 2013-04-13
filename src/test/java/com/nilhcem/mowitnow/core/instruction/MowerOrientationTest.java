package com.nilhcem.mowitnow.core.instruction;

import static com.nilhcem.mowitnow.core.instruction.MowerInstruction.*;
import static com.nilhcem.mowitnow.core.instruction.MowerOrientation.*;
import static org.fest.assertions.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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
		assertThat(MowerOrientation.getFromAngle(42)).isNull();
	}
}
