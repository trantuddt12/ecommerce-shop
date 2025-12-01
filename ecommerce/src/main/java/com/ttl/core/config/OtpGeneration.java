package com.ttl.core.config;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpGeneration {
	public String generationOtp(int pLength) {
		String lvDigit = "0123456789";
		Random lvRandom = new Random();
		StringBuilder lvOtp = new StringBuilder();
		for(int i = 0 ; i< pLength ; i++) {
			lvOtp.append(lvDigit.charAt(lvRandom.nextInt(lvDigit.length())));
		}
		return lvOtp.toString();
	}
}
