package com.bhardwaj.mini2.validation;

import com.bhardwaj.mini2.exceptions.InvalidOffsetException;

public class OffsetValidator implements NumericValidator {
	private static OffsetValidator instance;
	
	private OffsetValidator() {
		super();
	}
	
	@Override
	public boolean validate(String data) {
		try {
			int value = Integer.parseInt(data);
			if(value>=0) {
				return true;
			} else {
				throw new InvalidOffsetException();
			}
		} catch(NumberFormatException e) {
			throw new InvalidOffsetException();
		}
	}

	
	public static OffsetValidator getInstance() {
		if(instance == null) {
			synchronized (OffsetValidator.class) {
				if(instance == null) {
					instance = new OffsetValidator();
				}
			}
		}
		return instance;
	}
	

}
