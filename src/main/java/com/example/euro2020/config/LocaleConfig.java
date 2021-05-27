package com.example.euro2020.config;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
public class LocaleConfig {

	@PostConstruct
	public void init () {

		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
	}
}
