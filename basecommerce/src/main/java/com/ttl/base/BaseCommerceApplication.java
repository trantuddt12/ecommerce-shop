package com.ttl.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "com.ttl")
@EnableJpaAuditing
public class BaseCommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseCommerceApplication.class, args);
    }

}