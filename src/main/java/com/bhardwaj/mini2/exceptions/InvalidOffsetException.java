package com.bhardwaj.mini2.exceptions;

public class InvalidOffsetException extends RuntimeException {
	public InvalidOffsetException() {
		super("Error: invalid offset. offset must be a non negative integer.");
	}
}
