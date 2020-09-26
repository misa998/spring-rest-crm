package com.misa.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler
	public ResponseEntity<CustomerError> handler(CustomerNotFound e){
		CustomerError ce = new CustomerError();
		ce.setMessage(e.getMessage());
		ce.setTimeStamp(System.currentTimeMillis());
		ce.setStatus(HttpStatus.NOT_FOUND.value());
		
		return new ResponseEntity<> (ce, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<CustomerError> genericHandler(Exception e){
		CustomerError ce = new CustomerError();
		ce.setMessage(e.getMessage());
		ce.setTimeStamp(System.currentTimeMillis());
		ce.setStatus(HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<> (ce, HttpStatus.BAD_REQUEST);
	}
	
}
