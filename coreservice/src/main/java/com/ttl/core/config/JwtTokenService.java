package com.ttl.core.config;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ttl.common.constant.ITagCode;
import com.ttl.core.entities.User;
import com.ttl.core.handler.JwtAuthenticationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenService implements IJwtTokenService{

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    @Value("${jwt.refreshExpirationMs}")
    private long refreshExpirationMs;

	@Override
    public String generateToken(User pUser) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(pUser.getUsername())
                .claim("accountId", pUser.getId())
                .claim("email", pUser.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }
    @Override
    public String generateRefreshToken(User pUser) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshExpirationMs);

        return Jwts.builder()
                .setSubject(pUser.getUsername())
                .claim("type", "refresh")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
    	try {
    		Jwts.parserBuilder()
	            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
	            .build()
	            .parseClaimsJws(token);
    		
            return true;
    	} catch (ExpiredJwtException ex) {
            throw new JwtAuthenticationException(ITagCode.TOKEN_EXPIRED, ex); // Token expired
        } catch (UnsupportedJwtException ex) {
            throw new JwtAuthenticationException(ITagCode.TOKEN_UNSUPPORTED, ex); // Unsupported token
        } catch (MalformedJwtException ex) {
            throw new JwtAuthenticationException(ITagCode.TOKEN_MALFORMED, ex); // Malformed token
        } catch (SecurityException | SignatureException ex) {
            throw new JwtAuthenticationException(ITagCode.TOKEN_SIGNATURE_INVALID, ex); // Signature invalid
        } catch (IllegalArgumentException ex) {
            throw new JwtAuthenticationException(ITagCode.TOKEN_EMPTY, ex); // Token empty / invalid arg
        } catch (JwtException ex) {
            throw new JwtAuthenticationException(ITagCode.TOKEN_INVALID, ex); // Generic invalid token
        }
    }
    @Override
    public String extractUsername(String token) {
    	Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
