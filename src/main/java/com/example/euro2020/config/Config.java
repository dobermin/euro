package com.example.euro2020.config;

import com.example.euro2020.service.ConfigService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;

@Configuration
@EnableTransactionManagement
public class Config {

	private ConfigService configService;

	@Bean
	public ConfigService configService() {
		if (configService == null) configService = new ConfigService();
		return Objects.requireNonNullElse(configService, new ConfigService());
	}

}
