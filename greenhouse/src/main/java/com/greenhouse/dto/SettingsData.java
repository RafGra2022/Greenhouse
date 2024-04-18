package com.greenhouse.dto;

public record SettingsData(Boolean auto, Boolean voltage, Boolean heating, Float minHumidity, Float maxHumidity,
		Integer minTemperature) {

}
