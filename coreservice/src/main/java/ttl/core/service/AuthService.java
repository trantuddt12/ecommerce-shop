package com.ttl.core.service;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.ttl.common.constant.ITag;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.exception.UserNotFoundException;
import com.ttl.common.utilities.CoreUtils;
import com.ttl.core.config.JwtTokenService;
import com.ttl.core.entities.User;
import com.ttl.core.repository.RoleRepository;
import com.ttl.core.repository.UserRepository;
import com.ttl.core.request.LoginRequest;
import com.ttl.core.request.RegisterRequest;
import com.ttl.core.response.LoginResponse;
import com.ttl.core.service.login.LoginFactory;
import com.ttl.core.service.login.LoginStrategy;
import com.ttl.core.service.register.RegisterFactory;
import com.ttl.core.service.register.RegisterStrategy;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {

	private final LoginFactory mvLoginFactory;
	private final SerialService mvSerialService;
	private final RoleRepository mvRoleRepository;
	private final PasswordEncoder mvEncoder;
	private final UserRepository mvUserRepository;
	private final ApplicationEventPublisher mvEventPublisher;
	private final JwtTokenService mvJwtTokenService;
	private final RefreshTokenService mvRefreshTokenService;
	private final RegisterFactory mvRegisterFactory;
	
	
	public AuthService(LoginFactory pLoginFactory, SerialService pSerialService 
			,RoleRepository pRoleRepository , PasswordEncoder pEncoder
			,UserRepository pUserRepository, ApplicationEventPublisher pEventPublisher, JwtTokenService pJwtTokenService
			,RefreshTokenService pRefreshTokenService
			,RegisterFactory pRegisterFactory
			) {
		mvLoginFactory = pLoginFactory;
		mvSerialService  = pSerialService;
		mvRoleRepository = pRoleRepository;
		mvEncoder = pEncoder;
		mvUserRepository= pUserRepository;
		mvEventPublisher = pEventPublisher;
		this.mvJwtTokenService = pJwtTokenService;
		this.mvRefreshTokenService = pRefreshTokenService;
		mvRegisterFactory = pRegisterFactory;
	}
	
	public CompletableFuture<LoginResponse> login(LoginRequest pLoginRequest,  HttpServletRequest pRequest) throws BussinessException {
		LoginStrategy lvLoginStrategy = mvLoginFactory.getLoginStrategy(ITag.LOGIN_BY_USERNAME);
		return lvLoginStrategy.login(pLoginRequest, pRequest);
	}

	public User register(RegisterRequest pUserRequest) throws BussinessException {
		RegisterStrategy mvRegisterStrategy = mvRegisterFactory.getRegisterStrategy(ITag.REGISTER_BY_EMAIL);
		User lvUser = mvRegisterStrategy.register(pUserRequest);
		return lvUser;
	}
	
	public boolean verifyRegister(String pEmail) {
		if(!Objects.isNull(pEmail)) {
			int lvUpdateCount = mvUserRepository.updateState(pEmail);
			if(lvUpdateCount >0) return true;
		}
		return false;
	}
	
	public User getAccountByUsername(String pUsername) throws UserNotFoundException {
		User lvUser = mvUserRepository.findByUsername(pUsername)
				.orElseThrow(()-> new UserNotFoundException(pUsername, ITagCode.USER_NOT_FOUND, getClass()));
		return lvUser;
	}
	
	public String retrieveTokenByRefreshToken(String pRefreshToken) throws UserNotFoundException {
		if(mvRefreshTokenService.isValidRefreshToken(pRefreshToken)) {
			String lvUsername = mvJwtTokenService.extractUsername(pRefreshToken);
			User lvUser = getAccountByUsername(lvUsername);
			return mvJwtTokenService.generateRefreshToken(lvUser);
		}
		return "";
    }
	
	public Cookie logout(HttpServletRequest pHttpReq) {
		Cookie lvCookie = WebUtils.getCookie(pHttpReq, ITag.REFRESH_TOKEN);
		if(!CoreUtils.isNullStr(lvCookie)) {
			mvRefreshTokenService.disableRefreshToken(lvCookie.getValue());
		}
		Cookie rvCookie = new Cookie(ITag.REFRESH_TOKEN, null);
		lvCookie.setHttpOnly(true);
		lvCookie.setSecure(true); // nếu dùng HTTPS
		lvCookie.setPath("/");
		lvCookie.setMaxAge(0); // hết hạn ngay lập tức
		
        return rvCookie;
	}
}
