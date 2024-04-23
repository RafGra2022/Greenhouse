package com.greenhouse.controller;

public record GreenhouseSettingsResponse(Boolean auto, Boolean voltage, Boolean heating, Float minHumidity, Float maxHumidity,
		Integer minTemperature) {

}
