package com.ttl.core.service.login;

import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.coreservice.request.LoginRequest;
import com.tutv.epattern.coreservice.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.CompletableFuture;

public interface LoginStrategy {
	CompletableFuture<LoginResponse> login(LoginRequest pLoginRequest, HttpServletRequest pRequest) throws BussinessException;
	String strategyName();
}
