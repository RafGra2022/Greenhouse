package com.greenhouse.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.greenhouse.controller.GreenhouseSettingsView;
import com.greenhouse.model.GreenhouseSettingsEntity;
import com.greenhouse.model.GreenhouseSettingsMapper;
import com.greenhouse.repository.GreenhouseSettingsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GreenhouseSettingsService {
	
	private final GreenhouseSettingsRepository greenhouseSettingsRepository;
	private final GreenhouseSettingsMapper greenhouseSettingsMapper;
	private final WebClient espGreenhouse;

	@Transactional
	public String handleSensorSettings(GreenhouseSettingsView greenhouseSettingsView) {
		GreenhouseSettingsEntity greenhouseSettingsEntity =	greenhouseSettingsRepository.findTopByOrderByIdDesc();

		if(greenhouseSettingsEntity == null) {
			greenhouseSettingsRepository.save(greenhouseSettingsMapper.mapToGreenhouseSettingsEntity(null,greenhouseSettingsView));
		}
		
		greenhouseSettingsEntity = greenhouseSettingsMapper.mapToGreenhouseSettingsEntity(greenhouseSettingsEntity,greenhouseSettingsView);
		return "operation performed";
	}
	
	public GreenhouseSettingsView readSettings() {
		GreenhouseSettingsEntity greenhouseSettingsEntity =	greenhouseSettingsRepository.findTopByOrderByIdDesc();
		return greenhouseSettingsMapper.mapGreenhouseSettingsView(greenhouseSettingsEntity);
	}
	
	public void notifyDevice(GreenhouseSettingsView greenhouseSettingsView) {
		espGreenhouse.put()
        .uri(uriBuilder -> uriBuilder
                .path("/settings")
                .build())
        .body(BodyInserters.fromValue(greenhouseSettingsMapper.mapToSettingsData(greenhouseSettingsView)))
        .retrieve()
        .bodyToMono(String.class).block();
	}
}
