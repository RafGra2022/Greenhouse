package com.greenhouse.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.greenhouse.exception.EmptyRequestGreenhouseException;
import com.greenhouse.exception.NotFoundInDatabaseGreenhouseException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GreenhouseControllerAdvice {

	@ExceptionHandler(NotFoundInDatabaseGreenhouseException.class)
	public ResponseEntity<ErrorMessageResponse> entityNotFound(HttpServletRequest req, NotFoundInDatabaseGreenhouseException nFIDE){
		return createErrorMessageResponse(req.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR, nFIDE);
	}
	
	@ExceptionHandler(EmptyRequestGreenhouseException.class)
	public ResponseEntity<ErrorMessageResponse> emptyRequest(HttpServletRequest req, NotFoundInDatabaseGreenhouseException nFIDE){
		return createErrorMessageResponse(req.getRequestURL().toString(), HttpStatus.BAD_REQUEST, nFIDE);
	}

	private static ResponseEntity<ErrorMessageResponse> createErrorMessageResponse(String url, HttpStatus httpStatus,
			Exception ex) {
		log.error("Request url: {}", url, ex);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(ex.getMessage(), httpStatus.value());
		return ResponseEntity.status(httpStatus).headers(headers).body(errorMessageResponse);
	}
}
