//package com.ttl.core.config;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.TopicBuilder;
//
//import com.ttl.common.constant.ITag;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//	@Bean
//	public NewTopic userRegisteredTopic() {
//		return TopicBuilder.name(ITag.Kafka_Verification_Email).build();
//	}
//}
