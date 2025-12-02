package com.ttl.core.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ttl.core.entities.CustomUserDetails;
import com.ttl.core.entities.User;
import com.ttl.core.repository.UserRepository;

/**
 * 
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository mvUserRepository;
	
	public CustomUserDetailsService(UserRepository mvUserRepository) {
		super();
		this.mvUserRepository = mvUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String pUsername) throws UsernameNotFoundException {
		User lvUser = mvUserRepository.findByUsernameWithRoles(pUsername)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username : " +pUsername));
		return new CustomUserDetails(lvUser);
	}

}