package com.nilhcem.mowitnow;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Starting point of the application.
 *
 * <p>
 * Opens file passed in args and calls the Mower interpreter.
 * </p>
 */
public final class App {

	public static void main(String[] args) throws IOException {
		List<String> instructions = App.getInstructionsFromArgFile(args);
	}

	private static List<String> getInstructionsFromArgFile(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("App is expecting one (and only one) parameter: the mower instructions file");
		}

		File instructions = new File(args[0]);
		return FileUtils.readLines(instructions, "UTF-8");
	}
}
