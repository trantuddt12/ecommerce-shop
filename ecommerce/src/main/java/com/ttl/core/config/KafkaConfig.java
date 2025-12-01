package com.ttl.core.config;

import com.tutv.common.util.constant.ITag;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {
	
	@Bean
	public NewTopic userRegisteredTopic() {
		return TopicBuilder.name(ITag.Kafka_Verification_Email).build();
	}
}
