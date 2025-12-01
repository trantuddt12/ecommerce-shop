package com.ttl.core.service.login;

import com.tutv.common.util.constant.ITag;
import com.tutv.common.util.exception.BussinessException;
import com.tutv.epattern.coreservice.config.IJwtTokenService;
import com.tutv.epattern.coreservice.entities.CustomUserDetails;
import com.tutv.epattern.coreservice.entities.User;
import com.tutv.epattern.coreservice.request.LoginRequest;
import com.tutv.epattern.coreservice.response.LoginResponse;
import com.tutv.epattern.coreservice.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

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
