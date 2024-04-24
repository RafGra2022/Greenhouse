package com.greenhouse.controller;

import java.util.concurrent.ExecutorService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.greenhouse.exception.EmptyRequestGreenhouseException;
import com.greenhouse.exception.NotFoundInDatabaseGreenhouseException;
import com.greenhouse.exception.NotProcessedGreenhouseException;
import com.greenhouse.service.ErrorNotificationHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GreenhouseControllerAdvice {
	
	private final ErrorNotificationHandler errorNotificationHandler;
	private final ExecutorService executorService;

	@ExceptionHandler(NotFoundInDatabaseGreenhouseException.class)
	public ResponseEntity<ErrorMessageResponse> entityNotFound(HttpServletRequest req, NotFoundInDatabaseGreenhouseException nFIDE){
		return createErrorMessageResponse(req.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR, nFIDE);
	}
	
	@ExceptionHandler(EmptyRequestGreenhouseException.class)
	public ResponseEntity<ErrorMessageResponse> emptyRequest(HttpServletRequest req, EmptyRequestGreenhouseException eRGE){
		return createErrorMessageResponse(req.getRequestURL().toString(), HttpStatus.BAD_REQUEST, eRGE);
	}
	
	@ExceptionHandler(NotProcessedGreenhouseException.class)
	public ResponseEntity<ErrorMessageResponse> notProcessedException(HttpServletRequest req, NotProcessedGreenhouseException nPGE){
		return createErrorMessageResponse(req.getRequestURL().toString(), HttpStatus.SERVICE_UNAVAILABLE, nPGE);
	}
	
	@ExceptionHandler(WebClientRequestException.class)
	public ResponseEntity<ErrorMessageResponse> connectionError(HttpServletRequest req, WebClientRequestException wCRE){
		return createErrorMessageResponse(req.getRequestURL().toString(), HttpStatus.SERVICE_UNAVAILABLE, wCRE);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorMessageResponse> emptyBody(HttpServletRequest req, HttpMessageNotReadableException hMNRE){
		return createErrorMessageResponse(req.getRequestURL().toString(), HttpStatus.NO_CONTENT, hMNRE);
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessageResponse> unknownError(HttpServletRequest req, Exception ex){
		return createErrorMessageResponse(req.getRequestURL().toString(), HttpStatus.SERVICE_UNAVAILABLE, ex);
	}

	private ResponseEntity<ErrorMessageResponse> createErrorMessageResponse(String url, HttpStatus httpStatus,
			Exception ex) {
		log.error("Request url: {}", url, ex);
		executorService.execute(errorNotificationHandler.sendMail(httpStatus.name() + " reason :" + ex.getMessage()));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(ex.getMessage(), httpStatus.value());
		return ResponseEntity.status(httpStatus).headers(headers).body(errorMessageResponse);
	}
}
