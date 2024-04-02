package com.bhardwaj.mini2.exceptions;

public class InvalidSortTypeException extends RuntimeException {
	public InvalidSortTypeException() {
		super("Error: invalid sortType. sortType can be either age or name.");
	}
}
