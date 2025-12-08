package com.ttl.core.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
	
	@NotBlank(message = "E120")
	private String username;
	
	@NotBlank(message = "E121")
	private String password;
	
	@NotBlank(message = "E122")
	private String email;
	
	private String phonenumber;
//	
	private Long roleId;
//	
//	private boolean verifyOtp;
//	
//	private String otp; 
}
