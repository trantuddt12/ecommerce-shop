package com.ttl.core.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ttl.common.exception.BussinessException;
import com.ttl.common.exception.ErrorMessageLoader;
import com.ttl.common.exception.UserNotFoundException;
import com.ttl.core.response.ErrorMessage;

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
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {

            String message = mvErrorLoader.getMessage(
                    error.getDefaultMessage()
                    ,error.getArguments()
            );

            fieldErrors.put(error.getField(), message);
        });

        return ErrorMessage.builder()
                .error(fieldErrors)
                .status(HttpStatus.BAD_REQUEST.toString())
                .build();
    }
}
