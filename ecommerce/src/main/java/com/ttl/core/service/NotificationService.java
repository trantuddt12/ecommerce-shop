package com.ttl.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutv.common.util.constant.ITag;
import com.tutv.epattern.coreservice.event.RegisterVerficationEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

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
