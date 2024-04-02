package com.bhardwaj.mini2.exceptions;

public class NationalizeApiException extends RuntimeException {
	public NationalizeApiException() {
		super("Error: something went wrong on Nationalize API");
	}
}
