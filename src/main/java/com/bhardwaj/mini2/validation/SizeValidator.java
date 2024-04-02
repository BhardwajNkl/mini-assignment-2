package com.bhardwaj.mini2.validation;

import com.bhardwaj.mini2.exceptions.InvalidSizeException;

public class SizeValidator implements NumericValidator{
	private static SizeValidator instance;
	private SizeValidator() {
		super();
	}
	
	@Override
	public boolean validate(String data) {
		try {
			int value = Integer.parseInt(data);
			if(value>=1 && value<=5) {
				return true;
			} else {
				throw new InvalidSizeException();
			}
		} catch(NumberFormatException e) {
			throw new InvalidSizeException();
		}
	}
	
	public static SizeValidator getInstance() {
		if(instance == null) {
			synchronized (LimitValidator.class) {
				if(instance == null) {
					instance = new SizeValidator();
				}
			}
		}
		return instance;
	}

}
