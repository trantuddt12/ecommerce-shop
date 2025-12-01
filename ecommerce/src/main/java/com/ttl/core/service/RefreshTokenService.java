package com.ttl.core.service;

import com.tutv.common.util.constant.ITag;
import com.tutv.epattern.coreservice.config.IJwtTokenService;
import com.tutv.epattern.coreservice.entities.RefreshToken;
import com.tutv.epattern.coreservice.entities.User;
import com.tutv.epattern.coreservice.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
	
	private final RefreshTokenRepository mvResfreshTokenRepository;
	private final SerialService mvSerialService;
	private final IJwtTokenService mvTokenService;
	
	
    public RefreshTokenService(RefreshTokenRepository pResfreshTokenRepository, SerialService pSerialService,
			IJwtTokenService pTokenService) {
		super();
		this.mvResfreshTokenRepository = pResfreshTokenRepository;
		this.mvSerialService = pSerialService;
		this.mvTokenService = pTokenService;
	}

	public String saveRefreshToken(User pUser, HttpServletRequest pHttpReq) {
		String lvUserAgent = pHttpReq.getHeader("User-Agent");   // trình duyệt, OS
		String lvIPAddress = pHttpReq.getRemoteAddr();      
		
		String lvRefreshTokenValue = mvTokenService.generateRefreshToken(pUser);
		String lvTokenId =  mvSerialService.getNextSerial(ITag.TOKENID);
		
		RefreshToken lvRefreshToken =  RefreshToken.builder()
				.userId(pUser.getId())
				.tokenId(lvTokenId)
				.tokenValue(lvRefreshTokenValue)
				.deviceId(UUID.randomUUID().toString())
				.ipAddress(lvIPAddress)
				.userAgent(lvUserAgent)
				.revoked(false)
				.build();
		mvResfreshTokenRepository.save(lvRefreshToken);
		return lvRefreshTokenValue;
	}

    public boolean isValidRefreshToken(String pRefreshToken) {
    	Optional<RefreshToken> lvTokenStored = mvResfreshTokenRepository.findByTokenValue(pRefreshToken);
    	if(lvTokenStored.isEmpty() || lvTokenStored.get().isRevoked()) {
    		return false;
    	}
    	return mvTokenService.validateToken(pRefreshToken);
    }
    
    public void disableRefreshToken(String pRefreshToken) {
    	mvResfreshTokenRepository.disableToken(pRefreshToken);
    }
}
