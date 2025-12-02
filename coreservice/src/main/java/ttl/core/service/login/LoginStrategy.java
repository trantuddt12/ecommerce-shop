package com.ttl.core.service.login;

import java.util.concurrent.CompletableFuture;

import com.ttl.common.exception.BussinessException;
import com.ttl.core.request.LoginRequest;
import com.ttl.core.response.LoginResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface LoginStrategy {
	CompletableFuture<LoginResponse> login(LoginRequest pLoginRequest, HttpServletRequest pRequest) throws BussinessException;
	String strategyName();
}
