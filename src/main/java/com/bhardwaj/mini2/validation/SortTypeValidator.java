package com.bhardwaj.mini2.validation;

import com.bhardwaj.mini2.exceptions.InvalidSortTypeException;

public class SortTypeValidator implements AlphabeticValidator {
	private static SortTypeValidator instance;
	
	private SortTypeValidator() {
		super();
	}
	@Override
	public boolean validate(String data) {
		if(data.equalsIgnoreCase("Name") || data.equalsIgnoreCase("Age")){
			return true;
		} else {
			throw new InvalidSortTypeException();
		}
	}
	
	public static SortTypeValidator getInstance() {
		if(instance == null) {
			synchronized (SortTypeValidator.class) {
				if(instance == null) {
					instance = new SortTypeValidator();
				}
			}
		}
		return instance;
	}
	
}
