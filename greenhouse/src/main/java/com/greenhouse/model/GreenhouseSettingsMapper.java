package com.greenhouse.model;

import org.springframework.stereotype.Component;

import com.greenhouse.controller.GreenhouseSettingsRequest;
import com.greenhouse.controller.GreenhouseSettingsResponse;
import com.greenhouse.dto.SettingsData;

@Component
public class GreenhouseSettingsMapper {

	public GreenhouseSettingsEntity mapToGreenhouseSettingsEntity(GreenhouseSettingsEntity greenhouseSettingsEntity,
			GreenhouseSettingsRequest greenhouseSettingsRequest) {

		if (greenhouseSettingsEntity == null) {
			greenhouseSettingsEntity = new GreenhouseSettingsEntity();
		}

		greenhouseSettingsEntity.setAuto(greenhouseSettingsRequest.auto());
		greenhouseSettingsEntity.setVoltage(greenhouseSettingsRequest.voltage());
		greenhouseSettingsEntity.setHeating(greenhouseSettingsRequest.heating());
		greenhouseSettingsEntity.setMinHumidity(greenhouseSettingsRequest.minHumidity());
		greenhouseSettingsEntity.setMaxHumidity(greenhouseSettingsRequest.maxHumidity());
		greenhouseSettingsEntity.setMinTemperature(greenhouseSettingsRequest.minTemperature());

		return greenhouseSettingsEntity;
	}

	public GreenhouseSettingsResponse mapGreenhouseSettingsResponse(GreenhouseSettingsEntity greenhouseSettingsEntity) {
		return new GreenhouseSettingsResponse(greenhouseSettingsEntity.getAuto(), greenhouseSettingsEntity.getVoltage(),
				greenhouseSettingsEntity.getHeating(), greenhouseSettingsEntity.getMinHumidity(),
				greenhouseSettingsEntity.getMaxHumidity(), greenhouseSettingsEntity.getMinTemperature());
	}

	public SettingsData mapToSettingsData(GreenhouseSettingsEntity greenhouseSettingsEntity) {
		return new SettingsData(greenhouseSettingsEntity.getAuto(), greenhouseSettingsEntity.getVoltage(),
				greenhouseSettingsEntity.getHeating(), greenhouseSettingsEntity.getMinHumidity(),
				greenhouseSettingsEntity.getMaxHumidity(), greenhouseSettingsEntity.getMinTemperature());
	}
	
	public SettingsData mapToSettingsData(GreenhouseSettingsRequest greenhouseSettingsRequest) {
		return new SettingsData(greenhouseSettingsRequest.auto(), greenhouseSettingsRequest.voltage(),
				greenhouseSettingsRequest.heating(), greenhouseSettingsRequest.minHumidity(),
				greenhouseSettingsRequest.maxHumidity(), greenhouseSettingsRequest.minTemperature());
	}
}
