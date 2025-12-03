package com.ttl.core.entities;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
		// map Role → Permissions → GrantedAuthority
        return mvUser.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
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
