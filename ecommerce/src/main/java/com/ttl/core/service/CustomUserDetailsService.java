package com.ttl.core.service;

import com.tutv.epattern.coreservice.entities.CustomUserDetails;
import com.tutv.epattern.coreservice.entities.User;
import com.tutv.epattern.coreservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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