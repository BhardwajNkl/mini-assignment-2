package com.bhardwaj.mini2.exceptions;

public class UserNameException extends RuntimeException{
	public UserNameException() {
		super("Error: the Random User API returned a user with name containing non-alphabet characters. user creation failed.");
	}
}
