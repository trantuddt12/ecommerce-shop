package com.ttl.core.config;

import com.ttl.core.entities.User;

public interface IJwtTokenService {
    String generateToken(User pUser);
    String generateRefreshToken(User pUser);
    boolean validateToken(String pToken);
    String extractUsername(String pToken);
}
