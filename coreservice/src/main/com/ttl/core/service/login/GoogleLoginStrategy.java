package com.ttl.core.service.login;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.ttl.common.constant.ITagCode;
import com.ttl.common.exception.BussinessException;
import com.ttl.common.exception.UserNotFoundException;
import com.ttl.core.config.IJwtTokenService;
import com.ttl.core.dto.GoogleAccountInfo;
import com.ttl.core.entities.User;
import com.ttl.core.repository.UserRepository;
import com.ttl.core.request.LoginRequest;
import com.ttl.core.response.LoginResponse;

import jakarta.servlet.http.HttpServletRequest;

public class GoogleLoginStrategy implements LoginStrategy{

	@Value("${spring.security.oauth2.client.registration.google.client-id}")
	private String mvGoogleClientId;
	
	private final UserRepository mvUserRepository;
	private final IJwtTokenService mvTokenService;
	public GoogleLoginStrategy(UserRepository pUserRepository, IJwtTokenService pTokenService) {
		this.mvUserRepository = pUserRepository;
		this.mvTokenService = pTokenService;
	}

	@Override
	public CompletableFuture<LoginResponse> login(LoginRequest pLoginRequest, HttpServletRequest pRequest) throws BussinessException {
		String lvTokenId = pLoginRequest.getGoogleToken();
		GoogleAccountInfo lvAccInfo = verifyGoogleToken(lvTokenId);
		
		if(lvAccInfo == null) {
			throw new RuntimeException("Invalid Token!");
		}
		User lvUser = mvUserRepository.findByEmail(lvAccInfo.getEmail());
		if(lvUser == null) {
			throw new UserNotFoundException(lvAccInfo.getEmail(), ITagCode.USER_NOT_FOUND	, getClass());
		}
		String lvToken = mvTokenService.generateToken(lvUser);
		String lvRefreshToken = mvTokenService.generateRefreshToken(lvUser);
		return CompletableFuture.completedFuture(new LoginResponse(lvToken, lvRefreshToken));
	}

	private GoogleAccountInfo verifyGoogleToken(String lvTokenId) {
		try {
//	        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
//	                new NetHttpTransport(),
//	                JacksonFactory.getDefaultInstance())
//	                .setAudience(Collections.singletonList("YOUR_GOOGLE_CLIENT_ID"))
//	                .build();
	        
	        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
	            new NetHttpTransport(),
	            jsonFactory
	        )
	        .setAudience(Collections.singletonList(mvGoogleClientId))
	        .build();

	        GoogleIdToken idToken = verifier.verify(lvTokenId);
	        if (idToken != null) {
	            Payload payload = idToken.getPayload();

	            // Trích xuất thông tin từ token
	            String email = payload.getEmail();
	            // Bạn có thể lấy thêm name, picture nếu cần
	            return new GoogleAccountInfo(email);
	        } else {
	            return null; // Invalid token
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	@Override
	public String strategyName() {
		// TODO Auto-generated method stub
		return "google";
	}

}
