package com.ttl.common.exception;

public class MethodArgumentNotValidException extends BussinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MethodArgumentNotValidException(String message, String errorCode, Class<?> sourceClassName) {
		super(message, errorCode, sourceClassName);
		// TODO Auto-generated constructor stub
	}
}
