package com.ttl.core.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpEmailService {

	private final JavaMailSender mvMailSender;

	public OtpEmailService(JavaMailSender pMailSender) {
		super();
		this.mvMailSender = pMailSender;
	}
	
	public void sendOtpEmail(String pToEmail, String pOtp) {
		SimpleMailMessage lvEmail = new SimpleMailMessage();
		lvEmail.setTo(pToEmail);
		lvEmail.setSubject("OTP Verification Code");
		lvEmail.setText("Your OTP code is : " + pOtp + "\nThis code expires in 5 minutes");
		
		mvMailSender.send(lvEmail);
	}
	
}
