package com.ashokit.Exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AppExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<AppException> handleException (String exMsg){
		
		logger.error("Exception Occured : " + exMsg);
		
		AppException ex = new AppException();
		
		ex.setExCode("EX0003");
		
		ex.setExDesc(exMsg);
		
		ex.setExDate(LocalDateTime.now());
		
		return new ResponseEntity<AppException>(ex,HttpStatus.INTERNAL_SERVER_ERROR);
	} 

}
