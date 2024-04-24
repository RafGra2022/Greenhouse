package com.greenhouse.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.greenhouse.controller.GreenhouseSettingsRequest;
import com.greenhouse.controller.GreenhouseSettingsResponse;
import com.greenhouse.exception.NotFoundInDatabaseGreenhouseException;
import com.greenhouse.exception.NotProcessedGreenhouseException;
import com.greenhouse.model.GreenhouseSettingsEntity;
import com.greenhouse.model.GreenhouseSettingsMapper;
import com.greenhouse.repository.GreenhouseSettingsRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GreenhouseSettingsService {
	
	private final GreenhouseSettingsRepository greenhouseSettingsRepository;
	private final GreenhouseSettingsMapper greenhouseSettingsMapper;
	private final WebClient espGreenhouse;

	@Transactional
	public String handleSensorSettings(GreenhouseSettingsRequest greenhouseSettingsRequest) {
		GreenhouseSettingsEntity greenhouseSettingsEntity =	greenhouseSettingsRepository.findTopByOrderByIdDesc();

		if(greenhouseSettingsEntity == null) {
			greenhouseSettingsRepository.save(greenhouseSettingsMapper.mapToGreenhouseSettingsEntity(null,greenhouseSettingsRequest));
		}
		
		greenhouseSettingsEntity = greenhouseSettingsMapper.mapToGreenhouseSettingsEntity(greenhouseSettingsEntity,greenhouseSettingsRequest);
		return "operation performed";
	}
	
	public GreenhouseSettingsResponse readSettings(){
		GreenhouseSettingsEntity greenhouseSettingsEntity =	greenhouseSettingsRepository.findTopByOrderByIdDesc();
		if(greenhouseSettingsEntity == null) {
			throw new NotFoundInDatabaseGreenhouseException("Empty settings entity");
		}
		return greenhouseSettingsMapper.mapGreenhouseSettingsResponse(greenhouseSettingsEntity);
	}
	
	public void notifyDevice(GreenhouseSettingsRequest greenhouseSettingsRequest) {
		espGreenhouse.put()
        .uri(uriBuilder -> uriBuilder
                .path("/settings")
                .build())
        .body(BodyInserters.fromValue(greenhouseSettingsMapper.mapToSettingsData(greenhouseSettingsRequest)))
        .retrieve()
        .onStatus(status-> status != HttpStatus.OK, status -> 
        	Mono.error(new NotProcessedGreenhouseException("Something went wrong while communicate with esp8266")))
        .bodyToMono(String.class).block();
	}
}
