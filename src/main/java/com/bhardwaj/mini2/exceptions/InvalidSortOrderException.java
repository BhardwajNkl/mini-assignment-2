package com.bhardwaj.mini2.exceptions;

public class InvalidSortOrderException extends RuntimeException {
	public InvalidSortOrderException() {
		super("Error: invalid sortOrder. sortOrder can be either even or odd.");
	}
}
