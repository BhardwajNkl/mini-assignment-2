package com.bhardwaj.mini2.validation;

import com.bhardwaj.mini2.exceptions.InvalidLimitException;

public class LimitValidator implements NumericValidator{
	private static LimitValidator instance;
	private LimitValidator() {
		super();
	}
	
	@Override
	public boolean validate(String data) {
		try {
			int value = Integer.parseInt(data);
			if(value>=1 && value<=5) {
				return true;
			} else {
				throw new InvalidLimitException();
			}
		} catch(NumberFormatException e) {
			throw new InvalidLimitException();
		}
	}
	
	public static LimitValidator getInstance() {
		if(instance == null) {
			synchronized (LimitValidator.class) {
				if(instance == null) {
					instance = new LimitValidator();
				}
			}
		}
		return instance;
	}

}
