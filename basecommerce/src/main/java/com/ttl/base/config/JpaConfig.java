package com.ttl.base.config;

import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableJpaRepositories(basePackages = "com.ttl")
@EntityScan(basePackages = "com.ttl")
public class JpaConfig {

	@Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth == null || !auth.isAuthenticated() 
                || auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser")) {
                return Optional.of("SYSTEM");
            }
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                return Optional.of(userDetails.getUsername());
            }
            
            return Optional.of(principal.toString());
        };
    }
}



