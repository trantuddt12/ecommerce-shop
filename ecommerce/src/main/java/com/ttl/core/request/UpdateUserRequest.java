package com.ttl.core.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

	private String id;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String phonenumber;
	
	private List<String> roleId;
}
