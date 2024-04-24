package com.greenhouse.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorServiceConfiguration {

	@Bean
	ExecutorService executorService() {
		return  Executors.newFixedThreadPool(4);
	}
}
