package com.nilhcem.mowitnow.core.instruction.parser;

/**
 * Thrown when exception while parsing instructions.
 */
public final class ParserException extends Exception {
	private static final long serialVersionUID = 383759740316995604L;

	public ParserException(String message) {
		super(message);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}
}
