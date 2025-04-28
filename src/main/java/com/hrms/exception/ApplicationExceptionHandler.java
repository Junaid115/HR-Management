package com.hrms.exception;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hrms.util.ApiResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse> ConstraintViolationExceptionHandler(ConstraintViolationException ex){
		
		 Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
	        StringBuilder messages = new StringBuilder();

	        for (ConstraintViolation<?> violation : constraintViolations) {
	            messages.append(violation.getMessage()).append("\n");
	            System.out.println(violation.getMessage());
	        }
	        ApiResponse apires=new ApiResponse(messages.toString().trim(), false);
	       
		
		return new ResponseEntity<ApiResponse> (apires,HttpStatus.OK);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ApiResponse> handleInvalidPasswordException(InvalidPasswordException ex){
		String message = ex.getMessage();
		ApiResponse apires=new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse> (apires,HttpStatus.OK);
	}
	
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<ApiResponse> handleAlreadyExistsException(AlreadyExistsException ex){
		String message = ex.getMessage();
		ApiResponse apires=new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse> (apires,HttpStatus.OK);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiResponse> handleNotFoundException(NotFoundException ex){
		String message = ex.getMessage();
		ApiResponse apires=new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse> (apires,HttpStatus.OK);
	}

}
