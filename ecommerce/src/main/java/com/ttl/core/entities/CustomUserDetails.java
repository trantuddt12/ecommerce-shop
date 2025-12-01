package com.ttl.core.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final User mvUser;
	
	public CustomUserDetails(User user) {
		mvUser = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Set<String> lvPermissionName = mvUser.getRoles().stream()
//				.flatMap(role -> role.getPermissions().stream())
//				.map(permission -> permission.getName())
//				.collect(Collectors.toSet());
		Set<String> lvRole = mvUser.getRoles().stream()
				.map(role ->role.getId())
				.collect(Collectors.toSet());
				
		List<SimpleGrantedAuthority> authotities = lvRole.stream()
				.map(SimpleGrantedAuthority :: new)
				.collect(Collectors.toList());
		return authotities;
	}

	@Override
	public String getPassword() {
		return mvUser.getPassword();
	}

	@Override
	public String getUsername() {
		return mvUser.getUsername();
	}
	
	public User getUser() {
		return mvUser;
	}

}
