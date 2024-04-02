package com.bhardwaj.mini2.validation;

import com.bhardwaj.mini2.exceptions.InvalidSortOrderException;

public class SortOrderValidator implements AlphabeticValidator {
	private static SortOrderValidator instance;
	
	private SortOrderValidator() {
		super();
	}
	@Override
	public boolean validate(String data) {
		if(data.equalsIgnoreCase("Even") || data.equalsIgnoreCase("Odd")) {
			return true;
		} else {
			throw new InvalidSortOrderException();
		}
	}
	
	public static SortOrderValidator getInstance() {
		if(instance == null) {
			synchronized (SortOrderValidator.class) {
				if(instance == null) {
					instance = new SortOrderValidator();
				}
			}
		}
		return instance;
	}
	
}
