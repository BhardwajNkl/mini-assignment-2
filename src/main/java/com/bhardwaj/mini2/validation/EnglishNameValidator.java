package com.bhardwaj.mini2.validation;

import com.bhardwaj.mini2.exceptions.UserNameException;

public class EnglishNameValidator implements AlphabeticValidator {
	
	private static EnglishNameValidator instance;
	
	private EnglishNameValidator() {
		super();
	}
	
	@Override
	public boolean validate(String data) {
		String regex = "^[a-zA-Z]+$";
        if(data.matches(regex)) {
        	return true;
        } else {
        	throw new UserNameException();
        }
	}
	
	public static EnglishNameValidator getInstance() {
		if(instance == null) {
			synchronized (EnglishNameValidator.class) {
				if(instance == null) {
					instance = new EnglishNameValidator();
				}
			}
		}
		return instance;
	}

}

