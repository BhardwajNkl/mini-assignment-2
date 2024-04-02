package com.bhardwaj.mini2.exceptions;

public class InvalidLimitException extends RuntimeException{
	public InvalidLimitException() {
		super("Error: invalid limit. limit must be an integer between 1 and 5, both inclusive.");
	}
}
