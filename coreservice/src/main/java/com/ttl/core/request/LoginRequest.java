package com.ttl.core.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
	
	@NotBlank(message = "E100")
	private String username;
	@NotBlank(message = "E101")
	private String password;
	private String googleToken;
}
