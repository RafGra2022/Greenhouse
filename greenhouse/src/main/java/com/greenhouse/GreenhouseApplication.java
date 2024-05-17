package com.greenhouse;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class GreenhouseApplication {
	
//	@PostConstruct
//	public void setTimeZone() {
//		TimeZone.setDefault(TimeZone.getTimeZone("Europe/Warsaw"));
//	}

	public static void main(String[] args) {
		SpringApplication.run(GreenhouseApplication.class, args);
	}

}
