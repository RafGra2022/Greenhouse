package com.greenhouse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
public class WebClientConfiguration {

	@Bean
	WebClient espGreenhouse() {
		return WebClient.builder(
				).baseUrl("http://192.168.100.121:8081")
				.build();
	}
}
