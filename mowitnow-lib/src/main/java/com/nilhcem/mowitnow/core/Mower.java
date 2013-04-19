package com.nilhcem.mowitnow.core;

import com.nilhcem.mowitnow.core.instruction.MowerInstruction;
import com.nilhcem.mowitnow.core.instruction.MowerOrientation;

/**
 * Represents a lawn mower which is controlled by some given instructions.
 */
public final class Mower {
	private Coordinate coordinates;
	private MowerOrientation orientation;
	private Field field;

	/**
	 * Creates a mower.
	 *
	 * @param coordinates initial coordinates.
	 * @param orientation initial orientation.
	 * @param field environment where the mower will do its work (the mower needs to be aware of its environment).
	 */
	public Mower(Coordinate coordinates, MowerOrientation orientation, Field field) {
		this.coordinates = coordinates;
		this.orientation = orientation;
		this.field = field;
	}

	/**
	 * Returns a new instance of the mower's coordinates.
	 *
	 * @return the mower's coordinates.
	 */
	public Coordinate getCoordinates() {
		return new Coordinate(coordinates.getX(), coordinates.getY());
	}

	/**
	 * Retrieves the mower's orientation.
	 *
	 * @return the mower's orientation.
	 */
	public MowerOrientation getOrientation() {
		return orientation;
	}

	/**
	 * Returns a String representation of the mower's current position in its environment.
	 * <p>
	 * Example: "{@code 3 2 N}" means "x=3, y=2, N(orth)".
	 * </p>
	 *
	 * @return the mower's position.
	 */
	public String getPosition() {
		return String.format("%d %d %c", coordinates.getX(), coordinates.getY(), orientation.getLetter());
	}

	/**
	 * Runs a given instruction.
	 * <p>
	 * Mowers can't receive multiple instructions simultaneously.<br />
	 * Calls to this method <b>must</b> be sequential.
	 * </p>
	 *
	 * @param instruction the instruction to run.
	 * @return {@code true} if instruction was processed, or {@code false} if ignored.
	 * @see MowerInstruction
	 */
	public boolean process(MowerInstruction instruction) {
		boolean processed;

		if (instruction.equals(MowerInstruction.FORWARD)) {
			processed = moveForward();
		} else {
			processed = turn(instruction);
		}
		return processed;
	}

	private boolean moveForward() {
		Coordinate newCoords = MowerOrientation.getForwardCoordinates(orientation, coordinates);
		if (field.isValidLocation(newCoords)) {
			coordinates = newCoords;
			return true;
		}
		return false;
	}

	private boolean turn(MowerInstruction instruction) {
		MowerOrientation newOrientation = MowerOrientation.changeOrientation(orientation, instruction);
		if (newOrientation != null) {
			orientation = newOrientation;
			return true;
		}
		return false;
	}
}
