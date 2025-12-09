package com.ttl.core.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttl.common.constant.ITag;
import com.ttl.core.event.RegisterVerficationEvent;

@Service
public class NotificationService {

	private final EmailService mvEmailService;
	private final ObjectMapper mvMapper;
	public NotificationService(EmailService mvEmailService, ObjectMapper mvMapper) {
		super();
		this.mvEmailService = mvEmailService;
		this.mvMapper = mvMapper;
	}

	@KafkaListener(topics = ITag.Kafka_Verification_Email, groupId = "notification-group")
    public void consume(String message) throws JsonProcessingException {
		RegisterVerficationEvent event = mvMapper.readValue(message, RegisterVerficationEvent.class);
        mvEmailService.sendVerificationEmail(event.getEmail(), event.getToken());
    }

}
