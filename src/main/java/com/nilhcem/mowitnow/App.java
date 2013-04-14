package com.nilhcem.mowitnow;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.nilhcem.mowitnow.core.Mower;
import com.nilhcem.mowitnow.core.instruction.MowerInstruction;
import com.nilhcem.mowitnow.core.instruction.parser.InstructionsParser;

/**
 * Starting point of the application.
 * <p>
 * Opens file passed in args and calls the Mower interpreter.
 * </p>
 */
public final class App {

	private App() {
	}

	public static void main(String[] args) throws IOException {
		List<String> instructions = App.getInstructionsFromArgFile(args);
		runInstructions(instructions);
	}

	private static List<String> getInstructionsFromArgFile(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("App is expecting one (and only one) parameter: the mower instructions file");
		}

		File instructions = new File(args[0]);
		return FileUtils.readLines(instructions, "UTF-8");
	}

	private static void runInstructions(List<String> data) {
		InstructionsParser parser = new InstructionsParser(data);

		Mower mower = parser.getNextMower();
		while (mower != null) {
			List<MowerInstruction> instrs = parser.getInstructionsForMower(mower);
			for (MowerInstruction instr : instrs) {
				mower.process(instr);
			}
			System.out.println(mower.getPosition());
			mower = parser.getNextMower();
		}
	}
}
