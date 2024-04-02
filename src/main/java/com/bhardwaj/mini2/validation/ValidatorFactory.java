package com.bhardwaj.mini2.validation;

import org.springframework.stereotype.Component;

@Component
public class ValidatorFactory {
	public Validator createAlphabeticValidator(String validatorType) {
		switch (validatorType) {
		case "name":{
			Validator nameValidator = EnglishNameValidator.getInstance();
			return nameValidator;
		}
		case "sortType":{
			Validator sortTypeValidator = SortTypeValidator.getInstance();
			return sortTypeValidator;
		}
		case "sortOrder":{
			Validator sortOrderValidator = SortOrderValidator.getInstance();
			return sortOrderValidator;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + validatorType);
		}
	}
	
	public Validator createNumericValidator(String validatorType) {
		switch (validatorType) {
		case "size": {
			Validator sizeValidator = SizeValidator.getInstance();
			return sizeValidator;
		}
		case "limit": {
			Validator limitValidator = LimitValidator.getInstance();
			return limitValidator;
		}
		case "offset": {
			Validator offsetValidator = OffsetValidator.getInstance();
			return offsetValidator;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + validatorType);
		}
	}
}
