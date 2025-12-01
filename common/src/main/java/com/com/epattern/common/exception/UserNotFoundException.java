package com.com.epattern.common.exception;

public class UserNotFoundException extends BussinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message, String errorCode, Class<?> sourceClassName) {
		super(message, errorCode, sourceClassName);
		// TODO Auto-generated constructor stub
	}
}
