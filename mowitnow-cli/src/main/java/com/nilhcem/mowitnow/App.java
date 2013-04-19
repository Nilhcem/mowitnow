package com.nilhcem.mowitnow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.nilhcem.mowitnow.core.Mower;
import com.nilhcem.mowitnow.core.instruction.MowerInstruction;
import com.nilhcem.mowitnow.core.instruction.parser.InstructionsParser;
import com.nilhcem.mowitnow.core.instruction.parser.ParserException;

/**
 * Starting point of the application.
 * <p>
 * Opens file passed in args and calls the Mower interpreter.
 * </p>
 */
public final class App {

	private App() {
	}

	public static void main(String[] args) throws IOException, ParserException {
		List<String> instructions = App.getInstructionsFromArgFile(args);
		for (String position : getMowersPositions(instructions)) {
			System.out.println(position);
		}
	}

	/**
	 * Reads the contents of the file passed in args line by line to a List of Strings using the default encoding for the VM.
	 *
	 * @param args the instructions file path - should only contain one element.
	 * @return the list of Strings representing each line in the file passed in args, never {@code null}.
	 * @throws IOException in case of an I/O error.
	 */
	static List<String> getInstructionsFromArgFile(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("App is expecting one (and only one) parameter: the mower instructions file");
		}

		File instructions = new File(args[0]);
		return FileUtils.readLines(instructions, "UTF-8");
	}

	/**
	 * Runs instructions passed in parameter and returns a list of the mowers positions.
	 *
	 * @param data instructions to parse.
	 * @return a sorted list of all the mowers final positions.
	 * @throws ParserException when an exception occured while parsing instructions.
	 */
	static List<String> getMowersPositions(List<String> data) throws ParserException {
		List<String> positions = new ArrayList<String>();
		InstructionsParser parser = new InstructionsParser(data);

		List<Mower> mowers = parser.getMowers();
		for (Mower mower : mowers) {
			List<MowerInstruction> instrs = parser.getInstructionsForMower(mower);
			for (MowerInstruction instr : instrs) {
				mower.process(instr);
			}
			positions.add(mower.getPosition());
		}
		return positions;
	}
}
