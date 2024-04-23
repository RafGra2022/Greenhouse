package com.greenhouse.controller;

public record GreenhouseSettingsRequest(Boolean auto, Boolean voltage, Boolean heating, Float minHumidity, Float maxHumidity,
		Integer minTemperature) {

}
