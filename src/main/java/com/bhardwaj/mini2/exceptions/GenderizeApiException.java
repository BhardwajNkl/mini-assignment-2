package com.bhardwaj.mini2.exceptions;

public class GenderizeApiException extends RuntimeException {
	public GenderizeApiException() {
		super("Error: something went wrong on Genderize API");
	}
}
