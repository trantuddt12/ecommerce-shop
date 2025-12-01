package com.ttl.core.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationUtils {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper lvModelMapper = new ModelMapper();
		lvModelMapper.getConfiguration().setSkipNullEnabled(true);
		lvModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return lvModelMapper;
	}
}
