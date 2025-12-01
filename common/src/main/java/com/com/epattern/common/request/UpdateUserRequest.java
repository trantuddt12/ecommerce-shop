package com.tutv.epattern.common.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
