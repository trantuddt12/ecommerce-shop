package com.ttl.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ttl.common.constant.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private final ErrorMessageLoader mvErrorLoader;
	
	public GlobalExceptionHandler(ErrorMessageLoader pErrorLoader) {
		mvErrorLoader = pErrorLoader;
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<?>> handlerUserNotFound(UserNotFoundException pException){
		String lvMessage = mvErrorLoader.getMessage(pException.getErrorCode(), pException.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse.error(lvMessage, pException.getErrorCode(), pException.getSourceClassName()));
	}
	@ExceptionHandler(BussinessException.class)
	public ResponseEntity<ApiResponse<?>> handlerBusinessException(BussinessException pEx){
		String lvMessage = mvErrorLoader.getMessage(pEx.getErrorCode(), pEx.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ApiResponse.error(lvMessage, pEx.getErrorCode(), pEx.getSourceClassName()));
	}
}
