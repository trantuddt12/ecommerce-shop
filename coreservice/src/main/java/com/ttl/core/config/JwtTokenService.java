package com.ttl.core.config;

import com.ttl.core.entities.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

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
            Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("❌ Token hết hạn");
            return false;
        } catch (UnsupportedJwtException ex) {
            System.out.println("❌ Token không được hỗ trợ");
            return false;
        } catch (MalformedJwtException ex) {
            System.out.println("❌ Token sai định dạng");
            return false;
        } catch (JwtException ex) {
            System.out.println("ex");
            return false;
        } catch (IllegalArgumentException ex) {
            System.out.println("❌ Token rỗng hoặc null");
            return false;
        }
    }
    @Override
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
