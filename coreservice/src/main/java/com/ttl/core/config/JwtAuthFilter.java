package com.ttl.core.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	private final JwtTokenService mvJwtTokenService;
	private final UserDetailsService mvUserDetailsService;
	
	public JwtAuthFilter(JwtTokenService pJwtTokenService, UserDetailsService pDetailsService) {
		super();
		this.mvJwtTokenService = pJwtTokenService;
		this.mvUserDetailsService = pDetailsService;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String lvAuthHeader = request.getHeader("Authorization");
		request.getHeaderNames();
		if(!CoreUtils.isNullStr(lvAuthHeader) && lvAuthHeader.startsWith("Bearer ")){
			String lvToken = lvAuthHeader.substring(7);
			if(mvJwtTokenService.validateToken(lvToken)) {
				String lvUsername = mvJwtTokenService.extractUsername(lvToken);
				UserDetails lvUser = mvUserDetailsService.loadUserByUsername(lvUsername);
				UsernamePasswordAuthenticationToken lvAuthentication =
						new UsernamePasswordAuthenticationToken(lvUser, null, lvUser.getAuthorities());
				
				SecurityContextHolder.getContext().setAuthentication(lvAuthentication);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
