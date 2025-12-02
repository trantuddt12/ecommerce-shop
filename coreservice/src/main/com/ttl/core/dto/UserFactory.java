package com.ttl.core.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ttl.core.entities.Role;
import com.ttl.core.entities.User;
import com.ttl.core.repository.RoleRepository;

public class UserFactory {
	
	private final RoleRepository mvRoleRepository;
	
	public UserFactory(RoleRepository mvRoleRepository) {
		super();
		this.mvRoleRepository = mvRoleRepository;
	}
	private static Map<String, Role> mvRoleMap = new HashMap<String, Role>();

	public static User.UserBuilder createUserBuilder(UserType pType, String pUsername, String pEmail, String pPassword ){
		
		switch(pType) {
			case ADMIN :
				Role lvRole = Role.builder().id("ADMIN").build();
				return User.builder()
						.username(pUsername)
						.email(pEmail)
						.password(pPassword)
						.roles(Set.of(lvRole));
			case SELLER :
				lvRole = Role.builder().id("SELLER").build();
				return User.builder()
						.username(pUsername)
						.email(pEmail)
						.password(pPassword)
						.roles(Set.of(lvRole));
			
			case GUEST :
				lvRole = Role.builder().id("GUEST").build();
				return User.builder()
						.username(pUsername)
						.email(pEmail)
						.password(pPassword)
						.roles(Set.of(lvRole));
			default ://CUSTOMER :
				lvRole = Role.builder().id("CUSTOMER").build();
				return User.builder()
						.username(pUsername)
						.email(pEmail)
						.password(pPassword)
						.roles(Set.of(lvRole));
		}
	}
	
	public void getRoles(){
		List<Role> roles =  mvRoleRepository.findAll();
		mvRoleMap = new HashMap<String, Role>();
	}
}
