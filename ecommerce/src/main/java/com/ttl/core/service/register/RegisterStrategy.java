package com.ttl.core.service.register;

import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.coreservice.entities.User;
import com.tutv.epattern.coreservice.request.RegisterRequest;

public interface RegisterStrategy {
	User register(RegisterRequest pUserRequest) throws BussinessException;
	String registerName();
}
