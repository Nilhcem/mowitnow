package com.nilhcem.mowitnow;

import java.io.IOException;

import org.junit.*;
import static org.fest.assertions.api.Assertions.*;

public class AppTest {

	@Test(expected = IllegalArgumentException.class)
	public void appWithNoArgShouldThrowAnException() throws IOException {
		App.main(new String[] {});
	}

	@Test(expected = IllegalArgumentException.class)
	public void appWithMoreThanOneArgShouldThrowToo() throws IOException {
		App.main(new String[] { "one", "two" });
	}

	@Test(expected = IOException.class)
	public void appWithInvalidInstructionsFileShouldThrow() throws IOException {
		try {
			App.main(new String[] { "this_file_should_not_exist.txt" });
		} catch (IOException e) {
			assertThat(e).isNotInstanceOf(IllegalArgumentException.class);
			throw e;
		}
	}
}
