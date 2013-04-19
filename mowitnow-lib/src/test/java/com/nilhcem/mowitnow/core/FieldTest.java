package com.nilhcem.mowitnow.core;

import static org.fest.assertions.api.Assertions.assertThat;

import java.security.InvalidParameterException;

import org.junit.Test;

public class FieldTest {

	@Test(expected = InvalidParameterException.class)
	public void testInvalidWidth() {
		new Field(-3, 3);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidHeight() {
		new Field(3, -1);
	}

	@Test
	public void testInvalidLocations() {
		Field field = new Field(3, 3);
		assertThat(field.isValidLocation(new Coordinate(10, 10))).isFalse();
		assertThat(field.isValidLocation(new Coordinate(4, 0))).isFalse();
		assertThat(field.isValidLocation(new Coordinate(0, 4))).isFalse();
		assertThat(field.isValidLocation(new Coordinate(-1, 2))).isFalse();
		assertThat(field.isValidLocation(new Coordinate(1, -2))).isFalse();
	}

	@Test
	public void testValidLocations() {
		Field field = new Field(3, 3);
		assertThat(field.isValidLocation(new Coordinate(0, 0))).isTrue();
		assertThat(field.isValidLocation(new Coordinate(1, 1))).isTrue();
		assertThat(field.isValidLocation(new Coordinate(2, 2))).isTrue();
		assertThat(field.isValidLocation(new Coordinate(3, 3))).isTrue();
	}
}
