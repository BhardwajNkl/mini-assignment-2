package com.bhardwaj.mini2.exceptions;

public class RandomUserApiException extends RuntimeException {
	public RandomUserApiException() {
		super("Error: something went wrong on Random User API");
	}
}
