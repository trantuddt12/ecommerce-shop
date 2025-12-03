package com.ttl.core.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String phonenumber;
//	
	private Long roleId;
//	
//	private boolean verifyOtp;
//	
//	private String otp; 
}
