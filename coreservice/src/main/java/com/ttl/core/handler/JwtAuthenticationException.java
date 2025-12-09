package com.ttl.core.handler;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    private final String errorCode;

    public JwtAuthenticationException(String errorCode) {
        super(errorCode); // giữ message mặc định
        this.errorCode = errorCode;
    }

    public JwtAuthenticationException(String errorCode, Throwable cause) {
        super(errorCode, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}