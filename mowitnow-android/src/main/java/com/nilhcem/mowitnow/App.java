package com.nilhcem.mowitnow;

import com.nilhcem.mowitnow.core.instruction.parser.InstructionsParser;

import android.app.Application;

/**
 * Maintains global application state.
 */
public final class App extends Application {
	/**
	 * Parser to access instructions.
	 */
	private InstructionsParser mInstructions;

	public InstructionsParser getInstructions() {
		return mInstructions;
	}

	public void setInstructions(InstructionsParser instructions) {
		mInstructions = instructions;
	}
}
