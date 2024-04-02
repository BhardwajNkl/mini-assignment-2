package com.bhardwaj.mini2.exceptions;

public class InvalidSizeException extends RuntimeException {
	public InvalidSizeException() {
		super("Error: invalid size. size must be an integer between 1 and 5, both inclusive.");
	}
}
