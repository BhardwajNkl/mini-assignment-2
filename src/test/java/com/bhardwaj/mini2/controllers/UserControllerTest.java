package com.bhardwaj.mini2.controllers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bhardwaj.mini2.DTOs.ErrorResponse;
import com.bhardwaj.mini2.DTOs.GetApiSuccessResponse;
import com.bhardwaj.mini2.DTOs.UserPostRequestDTO;
import com.bhardwaj.mini2.entities.UserEntity;
import com.bhardwaj.mini2.exceptions.InvalidSizeException;
import com.bhardwaj.mini2.exceptions.InvalidSortTypeException;
import com.bhardwaj.mini2.services.UserService;
import com.bhardwaj.mini2.validation.Validator;
import com.bhardwaj.mini2.validation.ValidatorFactory;
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private ValidatorFactory validatorFactory;
    
    // 1. GET API TESTS
    
    @Test
    public void testGetUsers_valid_parameters() {
    	// arrange parameter values
        String sortType = "age";
        String sortOrder = "even";
        String limit = "2";
        String offset = "0";
        
        // mock the behavior of validator factory
        Validator validatorMock = mock(Validator.class);
        when(validatorFactory.createAlphabeticValidator("sortType")).thenReturn(validatorMock);
        when(validatorFactory.createAlphabeticValidator("sortOrder")).thenReturn(validatorMock);
        when(validatorFactory.createNumericValidator("limit")).thenReturn(validatorMock);
        when(validatorFactory.createNumericValidator("offset")).thenReturn(validatorMock);
        
        // mock the behavior of validators
        when(validatorMock.validate(sortType)).thenReturn(true);
        when(validatorMock.validate(sortOrder)).thenReturn(true);
        when(validatorMock.validate(limit)).thenReturn(true);
        when(validatorMock.validate(offset)).thenReturn(true);
        
        // mock the behavior of userService
        List<UserEntity> usersMock = new ArrayList<>();
        usersMock.add(
        		new UserEntity(1001,"Max","male","GM",36,"VERIFIED",new Date(), new Date())        		
        		);
        usersMock.add(
        		new UserEntity(2004,"Nitish","male","IN",23,"VERIFIED",new Date(), new Date())        		
        		);
        
        when(userService.getUsers(Integer.parseInt(limit), Integer.parseInt(offset)))
        .thenReturn(usersMock);
        when(userService.sortUsers(usersMock, sortType, sortOrder)).thenReturn(usersMock);
        when(userService.getTotalUserCount()).thenReturn(2L);
        
        // call the method under test
        ResponseEntity<?> responseEntity = userController.getUsers(sortType, sortOrder, limit, offset);

        // assertions for a successful response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof GetApiSuccessResponse);

        GetApiSuccessResponse responseBody = (GetApiSuccessResponse) responseEntity.getBody();
        assertNotNull(responseBody.getData());
        assertNotNull(responseBody.getPageInfo());
        assertEquals(2, responseBody.getData().size());

        // verify that relevant methods were called
        verify(validatorFactory).createAlphabeticValidator("sortType");
        verify(validatorFactory).createAlphabeticValidator("sortOrder");
        verify(validatorFactory).createNumericValidator("limit");
        verify(validatorFactory).createNumericValidator("offset");
        verify(validatorMock).validate(sortType);
        verify(validatorMock).validate(sortOrder);
        verify(validatorMock).validate(limit);
        verify(validatorMock).validate(offset);
        verify(userService).getTotalUserCount();
        verify(userService).getUsers(Integer.parseInt(limit), Integer.parseInt(offset));
        verify(userService).sortUsers(usersMock, sortType, sortOrder);
    }
    

    @Test
    public void testGetUsers_invalid_parameter() {
    	// arrange parameter values
        String sortType = "names"; // invalid
        String sortOrder = "even";
        String limit = "4";
        String offset = "5";
        
        // mock the behavior of validator factory
        Validator validatorMock = mock(Validator.class);
        when(validatorFactory.createAlphabeticValidator("sortType")).thenReturn(validatorMock);
        when(validatorFactory.createAlphabeticValidator("sortOrder")).thenReturn(validatorMock);
        when(validatorFactory.createNumericValidator("limit")).thenReturn(validatorMock);
        when(validatorFactory.createNumericValidator("offset")).thenReturn(validatorMock);

        // mock the validation exception
        doThrow(new InvalidSortTypeException()).when(validatorMock).validate(sortType);
        
        // call the method under test
        ResponseEntity<?> responseEntity = userController.getUsers(sortType, sortOrder, limit, offset);

        // assertions for the error response
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ErrorResponse);

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("Error: invalid sortType. sortType can be either age or name.", errorResponse.getMessage());
        assertEquals(400, errorResponse.getCode());
        assertNotNull(errorResponse.getTimestamp());

        // verify that relevant methods were called
        verify(validatorFactory).createAlphabeticValidator("sortType");
        verify(validatorFactory).createAlphabeticValidator("sortOrder");
        verify(validatorFactory).createNumericValidator("limit");
        verify(validatorFactory).createNumericValidator("offset");
        verify(validatorMock).validate(sortType);
    }
    
    @Test
    public void testGetUsers_unexpected_exception() {
    	// arrange parameter values
        String sortType = "name";
        String sortOrder = "odd";
        String limit = "5";
        String offset = "10";
        
        // mock the behavior of validator factory
        Validator validatorMock = mock(Validator.class);
        when(validatorFactory.createAlphabeticValidator("sortType")).thenReturn(validatorMock);
        when(validatorFactory.createAlphabeticValidator("sortOrder")).thenReturn(validatorMock);
        when(validatorFactory.createNumericValidator("limit")).thenReturn(validatorMock);
        when(validatorFactory.createNumericValidator("offset")).thenReturn(validatorMock);
        
        // mock the behavior of validators
        when(validatorMock.validate(sortType)).thenReturn(true);
        when(validatorMock.validate(sortOrder)).thenReturn(true);
        when(validatorMock.validate(limit)).thenReturn(true);
        when(validatorMock.validate(offset)).thenReturn(true);

        // mock an unexpected exception
        doThrow(new RuntimeException("Error: Unexpected Exception")).when(userService).getTotalUserCount();
        
        // call the method under test
        ResponseEntity<?> responseEntity = userController.getUsers(sortType, sortOrder, limit, offset);

        // assertions for the error response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof ErrorResponse);

        ErrorResponse errorResponse = (ErrorResponse) responseEntity.getBody();
        assertEquals("Error: Unexpected Exception", errorResponse.getMessage());
        assertEquals(500, errorResponse.getCode());
        assertNotNull(errorResponse.getTimestamp());

        // verify that relevant methods were called
        verify(validatorFactory).createAlphabeticValidator("sortType");
        verify(validatorFactory).createAlphabeticValidator("sortOrder");
        verify(validatorFactory).createNumericValidator("limit");
        verify(validatorFactory).createNumericValidator("offset");
        verify(userService).getUsers(Integer.parseInt(limit), Integer.parseInt(offset));
        verify(userService).getTotalUserCount();
    }
    
    
    
    // 2. POST API TESTS
    @Test
    public void testCreateUsers_valid_request() {
    	// mock the request body
    	UserPostRequestDTO requestMock = new UserPostRequestDTO(2);
    	
    	// mock the behavior of validator factory
    	Validator validatorMock = mock(Validator.class);
    	when(validatorFactory.createNumericValidator("size")).thenReturn(validatorMock);
    	
    	// mock the behavior of the validator
    	when(validatorMock.validate(Integer.toString(requestMock.getSize()))).thenReturn(true);
    	
    	// mock the behavior of userService
    	List<UserEntity> createdUsersMock = new ArrayList<>();
    	createdUsersMock.add(
    			new UserEntity(7823, "Manish","male","IN",22,"VERIFIED",new Date(), new Date())
    			);
    	createdUsersMock.add(
    			new UserEntity(7823, "Amar","male","IN",23,"VERIFIED",new Date(), new Date())
    			);
    	
    	when(userService.createUsers(requestMock.getSize())).thenReturn(createdUsersMock);
    	
    	// call the method under test
    	ResponseEntity<?> respoonse = userController.createUsers(requestMock);
    	
    	// assertions for a successful response
    	assertEquals(HttpStatus.CREATED, respoonse.getStatusCode());
    	assertNotNull(respoonse.getBody());
    	
    	List<UserEntity> users = (List<UserEntity>) respoonse.getBody();
    	assertEquals(2, users.size());
    	
    	// verify that the relevant methods were called
    	verify(validatorFactory).createNumericValidator("size");
    	verify(validatorMock).validate(Integer.toString(requestMock.getSize()));
    	verify(userService).createUsers(requestMock.getSize());
    }
    
    @Test
    public void testCreateUsers_request_with_invalid_size() {
    	// mock the request body
    	UserPostRequestDTO requestMock = new UserPostRequestDTO(10); // invalid size
    	
    	// mock the behavior of validator factory
    	Validator validatorMock = mock(Validator.class);
    	when(validatorFactory.createNumericValidator("size")).thenReturn(validatorMock);
    	
    	// mock the behavior of validator
    	doThrow(new InvalidSizeException()).when(validatorMock).validate(Integer.toString(requestMock.getSize()));
    	
    	// call the method under test
    	ResponseEntity<?> respoonse = userController.createUsers(requestMock);
    	
    	// assertions for the error response
    	assertEquals(HttpStatus.BAD_REQUEST, respoonse.getStatusCode());
    	assertNotNull(respoonse.getBody());
    	assertTrue(respoonse.getBody() instanceof ErrorResponse);
    	
    	ErrorResponse errorResponse = (ErrorResponse) respoonse.getBody();
    	assertEquals("Error: invalid size. size must be an integer between 1 and 5, both inclusive.",errorResponse.getMessage());
    	assertEquals(400, errorResponse.getCode());
    	
    	// verify that relevant methods were called
    	verify(validatorFactory).createNumericValidator("size");
    	verify(validatorMock).validate(Integer.toString(requestMock.getSize()));
    }
    
    @Test
    public void testCreateUsers_unexpected_exception() {
    	// mock the request body
    	UserPostRequestDTO requestMock = new UserPostRequestDTO(2);
    	
    	// mock the behavior of validator factory
    	Validator validatorMock = mock(Validator.class);
    	when(validatorFactory.createNumericValidator("size")).thenReturn(validatorMock);
    	
    	// mock the behavior of validator
    	when(validatorMock.validate(Integer.toString(requestMock.getSize()))).thenReturn(true);
    	
    	// mock an unexpected exception
    	doThrow(new RuntimeException("Error: Could Not Create Users")).when(userService).createUsers(requestMock.getSize());
    	
    	// call the method under test
    	ResponseEntity<?> respoonse = userController.createUsers(requestMock);
    	
    	// assertions for the error response
    	assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respoonse.getStatusCode());
    	assertNotNull(respoonse.getBody());
    	assertTrue(respoonse.getBody() instanceof ErrorResponse);
    	
    	ErrorResponse errorResponse = (ErrorResponse) respoonse.getBody();
    	assertEquals("Error: Could Not Create Users",errorResponse.getMessage());
    	
    	// verify that relevant methods were called
    	verify(validatorFactory).createNumericValidator("size");
    	verify(validatorMock).validate(Integer.toString(requestMock.getSize()));
    	verify(userService).createUsers(requestMock.getSize());
    }
}
