package com.tutv.common.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BussinessException extends Exception {

	private String errorCode;
	private Class<?> sourceClassName;
	public BussinessException(String message , String errorCode, Class<?> sourceClassName) {
		super(message);
		this.errorCode = errorCode;
		this.sourceClassName = sourceClassName;
	}
}
