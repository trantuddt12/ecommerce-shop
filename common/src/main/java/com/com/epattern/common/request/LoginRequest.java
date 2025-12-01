package com.tutv.epattern.common.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
	private String username;
	private String password;
	private String googleToken;
}
