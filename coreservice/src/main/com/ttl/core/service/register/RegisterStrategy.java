package com.ttl.core.service.register;

import com.ttl.common.exception.BussinessException;
import com.ttl.core.entities.User;
import com.ttl.core.request.RegisterRequest;

public interface RegisterStrategy {
	User register(RegisterRequest pUserRequest) throws BussinessException;
	String registerName();
}
