package com.nilhcem.mowitnow.core;

import static org.fest.assertions.api.Assertions.assertThat;

import java.awt.Point;
import java.security.InvalidParameterException;

import org.junit.Test;

public class FieldTest {

	@Test(expected = InvalidParameterException.class)
	public void testInvalidWidth() {
		new Field(-3, 3);
	}

	@Test(expected = InvalidParameterException.class)
	public void testInvalidHeight() {
		new Field(3, 0);
	}

	@Test
	public void testInvalidLocations() {
		Field field = new Field(3, 3);
		assertThat(field.isValidLocation(new Point(10, 10))).isFalse();
		assertThat(field.isValidLocation(new Point(3, 0))).isFalse();
		assertThat(field.isValidLocation(new Point(0, 3))).isFalse();
		assertThat(field.isValidLocation(new Point(-1, 2))).isFalse();
		assertThat(field.isValidLocation(new Point(1, -2))).isFalse();
	}

	@Test
	public void testValidLocations() {
		Field field = new Field(3, 3);
		assertThat(field.isValidLocation(new Point(0, 0))).isTrue();
		assertThat(field.isValidLocation(new Point(1, 1))).isTrue();
		assertThat(field.isValidLocation(new Point(2, 2))).isTrue();
	}
}
