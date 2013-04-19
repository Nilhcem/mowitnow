package com.nilhcem.mowitnow;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.junit.Test;

import com.nilhcem.mowitnow.core.instruction.parser.ParserException;

public class AppTest {

	@Test(expected = IllegalArgumentException.class)
	public void appWithNoArgShouldThrowAnException() throws IOException, ParserException {
		App.main(new String[] {});
	}

	@Test(expected = IllegalArgumentException.class)
	public void appWithMoreThanOneArgShouldThrowToo() throws IOException, ParserException {
		App.main(new String[] { "one", "two" });
	}

	@Test(expected = IOException.class)
	public void appWithInvalidInstructionsFileShouldThrow() throws IOException, ParserException {
		try {
			App.main(new String[] { "this_file_should_not_exist.txt" });
		} catch (IOException e) {
			assertThat(e).isNotInstanceOf(IllegalArgumentException.class);
			throw e;
		}
	}

	@Test
	public void testWithWorkingInstructions1() throws IOException, ParserException {
		String[] expected = new String[] { "1 3 N", "5 1 E" };
		assertThat(getResultsFromResourceFile("instructions_working_1.txt")).contains(expected);
	}

	@Test
	public void testWithWorkingInstructions2() throws IOException, ParserException {
		String[] expected = new String[] { "0 0 E" };
		assertThat(getResultsFromResourceFile("instructions_working_2.txt")).contains(expected);
	}

	@Test
	public void testWithWorkingInstructions3() throws IOException, ParserException {
		String[] expected = new String[] { "0 0 S", "3 0 E", "3 1 W" };
		assertThat(getResultsFromResourceFile("instructions_working_3.txt")).contains(expected);
	}

	@Test(expected = ParserException.class)
	public void testWithInvalidInstructions1() throws IOException, ParserException {
		getResultsFromResourceFile("instructions_invalid_1.txt");
	}

	@Test(expected = ParserException.class)
	public void testWithInvalidInstructions2() throws IOException, ParserException {
		getResultsFromResourceFile("instructions_invalid_2.txt");
	}

	@Test(expected = ParserException.class)
	public void testWithInvalidInstructions3() throws IOException, ParserException {
		getResultsFromResourceFile("instructions_invalid_3.txt");
	}

	private List<String> getResultsFromResourceFile(String resource) throws IOException, ParserException {
		List<String> instructions = App.getInstructionsFromArgFile(
				new String[] { getResource(resource) });
		return App.getMowersPositions(instructions);
	}

	/**
	 * Gets a resource from a file name.
	 *
	 * @param name name of the resource.
	 * @return the absolute file path.
	 * @throws UnsupportedEncodingException if the resource file path decoding could not be done.
	 */
	private String getResource(String name) throws UnsupportedEncodingException {
		URL url = Class.class.getResource("/" + name);
		String path = URLDecoder.decode(url.getFile(), "UTF-8");
		return new File(path).getAbsolutePath();
	}
}
