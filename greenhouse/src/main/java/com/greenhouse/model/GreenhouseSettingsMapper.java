package com.greenhouse.model;

import org.springframework.stereotype.Component;

import com.greenhouse.controller.GreenhouseSettingsView;
import com.greenhouse.dto.SettingsData;

@Component
public class GreenhouseSettingsMapper {

	public GreenhouseSettingsEntity mapToGreenhouseSettingsEntity(GreenhouseSettingsEntity greenhouseSettingsEntity,
			GreenhouseSettingsView greenhouseSettingsView) {

		if (greenhouseSettingsEntity == null) {
			greenhouseSettingsEntity = new GreenhouseSettingsEntity();
		}

		greenhouseSettingsEntity.setAuto(greenhouseSettingsView.auto());
		greenhouseSettingsEntity.setVoltage(greenhouseSettingsView.voltage());
		greenhouseSettingsEntity.setHeating(greenhouseSettingsView.heating());
		greenhouseSettingsEntity.setMinHumidity(greenhouseSettingsView.minHumidity());
		greenhouseSettingsEntity.setMaxHumidity(greenhouseSettingsView.maxHumidity());
		greenhouseSettingsEntity.setMinTemperature(greenhouseSettingsView.minTemperature());

		return greenhouseSettingsEntity;
	}

	public GreenhouseSettingsView mapGreenhouseSettingsView(GreenhouseSettingsEntity greenhouseSettingsEntity) {
		return new GreenhouseSettingsView(greenhouseSettingsEntity.getAuto(), greenhouseSettingsEntity.getVoltage(),
				greenhouseSettingsEntity.getHeating(), greenhouseSettingsEntity.getMinHumidity(),
				greenhouseSettingsEntity.getMaxHumidity(), greenhouseSettingsEntity.getMinTemperature());
	}

	public SettingsData mapToSettingsData(GreenhouseSettingsEntity greenhouseSettingsEntity) {
		return new SettingsData(greenhouseSettingsEntity.getAuto(), greenhouseSettingsEntity.getVoltage(),
				greenhouseSettingsEntity.getHeating(), greenhouseSettingsEntity.getMinHumidity(),
				greenhouseSettingsEntity.getMaxHumidity(), greenhouseSettingsEntity.getMinTemperature());
	}
	
	public SettingsData mapToSettingsData(GreenhouseSettingsView greenhouseSettingsView) {
		return new SettingsData(greenhouseSettingsView.auto(), greenhouseSettingsView.voltage(),
				greenhouseSettingsView.heating(), greenhouseSettingsView.minHumidity(),
				greenhouseSettingsView.maxHumidity(), greenhouseSettingsView.minTemperature());
	}
}
