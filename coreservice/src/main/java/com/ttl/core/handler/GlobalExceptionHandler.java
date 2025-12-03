package com.ttl.core.handler;

import com.ttl.common.exception.BussinessException;
import com.ttl.common.exception.ErrorMessageLoader;
import com.ttl.common.exception.UserNotFoundException;
import com.ttl.core.response.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final ErrorMessageLoader mvErrorLoader;

	public GlobalExceptionHandler(ErrorMessageLoader pErrorLoader) {
		mvErrorLoader = pErrorLoader;
	}

	@ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handlerUserNotFound(UserNotFoundException e){
        String message = mvErrorLoader.getMessage(e.getErrorCode(), e.getMessage());
        return ErrorMessage.builder()
                .error(message)
                .status(HttpStatus.NOT_FOUND.toString())
                .build();
	}
	@ExceptionHandler(BussinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handlerBusinessException(BussinessException e){
        String message = mvErrorLoader.getMessage(e.getErrorCode(), e.getMessage());
        return ErrorMessage.builder()
                .error(message)
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .build();
	}

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handlerRuntimeException(RuntimeException e){
//        String message = mvErrorLoader.getMessage(e.getErrorCode(), e.getMessage());
        return ErrorMessage.builder()
                .error(e.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .build();
    }
}
