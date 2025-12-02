package com.ttl.core.service.login;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.ttl.common.constant.ITag;
import com.ttl.common.exception.BussinessException;
import com.ttl.core.config.IJwtTokenService;
import com.ttl.core.entities.CustomUserDetails;
import com.ttl.core.entities.User;
import com.ttl.core.request.LoginRequest;
import com.ttl.core.response.LoginResponse;
import com.ttl.core.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class PaswordLoginStrategy implements LoginStrategy {

	private final IJwtTokenService mvJwtTokenService;
	private final AuthenticationManager mvAuthenManager;
	private final RefreshTokenService mvRefreshTokenService;
  
	public PaswordLoginStrategy(IJwtTokenService pTokenService
			, AuthenticationManager pAuthenManager
			, RefreshTokenService pRefreshTokenService) {
		mvJwtTokenService = pTokenService;
		mvAuthenManager= pAuthenManager;
		mvRefreshTokenService = pRefreshTokenService;
	}
	@Async("taskExecutor")
	@Override
	public CompletableFuture<LoginResponse> login(LoginRequest pLoginRequest, HttpServletRequest pHttpReq) throws BussinessException {
		
		Authentication lvAuthentication = mvAuthenManager.authenticate(new UsernamePasswordAuthenticationToken(pLoginRequest.getUsername(), pLoginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(lvAuthentication);
		
		CustomUserDetails lvUserDetails =  (CustomUserDetails) lvAuthentication.getPrincipal();
		
		User lvUser = lvUserDetails.getUser();
		
//		User lvUser = mvRepository.findByUsername(pLoginRequest.getUsername())
//				.orElseThrow(() -> new UserNotFoundException(pLoginRequest.getUsername(), ITagErrorCode.USER_NOT_FOUND	, getClass()));
//		
//		if(!mvEncoder.matches(pLoginRequest.getPassword() , lvUser.getPassword() )) {
//			throw new BussinessException(pLoginRequest.getUsername(), ITagErrorCode.INVALID_PASSWORD, getClass());
//		}
		String lvToken = mvJwtTokenService.generateToken(lvUser);
		String lvRefreshTokenValue = mvRefreshTokenService.saveRefreshToken(lvUser, pHttpReq);
		return CompletableFuture.completedFuture(new LoginResponse(lvToken, lvRefreshTokenValue));
	}

	@Override
	public String strategyName() {
		return ITag.LOGIN_BY_USERNAME;
	}
}
