package com.bhardwaj.mini2.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bhardwaj.mini2.DTOs.ErrorResponse;
import com.bhardwaj.mini2.DTOs.GetApiSuccessResponse;
import com.bhardwaj.mini2.DTOs.PageInfo;
import com.bhardwaj.mini2.DTOs.UserPostRequestDTO;
import com.bhardwaj.mini2.entities.UserEntity;
import com.bhardwaj.mini2.exceptions.InvalidLimitException;
import com.bhardwaj.mini2.exceptions.InvalidOffsetException;
import com.bhardwaj.mini2.exceptions.InvalidSizeException;
import com.bhardwaj.mini2.exceptions.InvalidSortOrderException;
import com.bhardwaj.mini2.exceptions.InvalidSortTypeException;
import com.bhardwaj.mini2.services.UserService;
import com.bhardwaj.mini2.validation.Validator;
import com.bhardwaj.mini2.validation.ValidatorFactory;

@RestController
public class UserController {
	
	private ValidatorFactory validatorFactory;
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService, ValidatorFactory validatorFactory) {
		this.userService = userService;
		this.validatorFactory = validatorFactory;
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getUsers(@RequestParam("sortType") String sortType, @RequestParam("sortOrder") String sortOrder,
			@RequestParam("limit") String limit, @RequestParam("offset") String offset ) {
		
		try {
			// validate query parameters
			Validator sortTypeValidator = validatorFactory.createAlphabeticValidator("sortType");
			Validator sortOrderValidator = validatorFactory.createAlphabeticValidator("sortOrder");
			Validator limitValidator = validatorFactory.createNumericValidator("limit");
			Validator offsetValidator = validatorFactory.createNumericValidator("offset");
			
			sortTypeValidator.validate(sortType);
			sortOrderValidator.validate(sortOrder);
			limitValidator.validate(limit);
			offsetValidator.validate(offset);
				
			// get the list of users from the database
			int limitValue = Integer.parseInt(limit);
			int offsetValue = Integer.parseInt(offset);
			List<UserEntity> users = userService.getUsers(limitValue, offsetValue);
			
			// sort the list as requested
			users = userService.sortUsers(users, sortType, sortOrder);
			
			// get the total count of users in the database
			long totalUsersCount = userService.getTotalUserCount();
			
			// construct page info to be returned as part of the response
			PageInfo pageInfo = new PageInfo(totalUsersCount, limitValue, offsetValue);
			
			// construct the object representing the response body data
			GetApiSuccessResponse getApiSuccessResponse = new GetApiSuccessResponse();
			getApiSuccessResponse.setData(users);
			getApiSuccessResponse.setPageInfo(pageInfo);
			
			// return the response with HTTP status OK
			return ResponseEntity.status(200).body(getApiSuccessResponse);
		} catch(InvalidSortTypeException | InvalidSortOrderException | InvalidLimitException |
				InvalidOffsetException QueryParamException) {
			// construct the custom error object
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage(QueryParamException.getMessage());
			errorResponse.setCode(400);
			errorResponse.setTimestamp(new Date());
			// return the custom error response with HTTP status BAD_REQUEST
			return ResponseEntity.status(400).body(errorResponse);
		} catch (Exception e) {
			// something unexpected happened on our end
			// return a custom error response with HTTP status INTERNAL_SERVER_ERROR
			return ResponseEntity.status(500).body(new ErrorResponse(e.getMessage(), 500, new Date()));
		}
	}
	
	@PostMapping("/users")
	public ResponseEntity<?> createUsers(@RequestBody UserPostRequestDTO userPostRequestDTO) {
		try {
			// validate request payload data
			Validator sizeValidator = validatorFactory.createNumericValidator("size");
			sizeValidator.validate(Integer.toString(userPostRequestDTO.getSize()));
			
			// create users
			List<UserEntity> users = userService.createUsers(userPostRequestDTO.getSize());
			
			// return the list of created users in the response with HTTP status CREATED
			return ResponseEntity.status(201).body(users);
			
		} catch (InvalidSizeException exception) {
			// construct a custom error object
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage(exception.getMessage());
			errorResponse.setCode(400);
			errorResponse.setTimestamp(new Date());
			
			// return the custom error response with HTTP status as BAD_REQUEST
			return ResponseEntity.status(400).body(errorResponse); 
		} catch(Exception exception) {
			// something unexpected happened
			// return a custom error response with HTTP status INTERNAL_SERVER_ERROR
			return ResponseEntity.status(500).body(new ErrorResponse(exception.getMessage(), 500, new Date()));
		}
	}
}
