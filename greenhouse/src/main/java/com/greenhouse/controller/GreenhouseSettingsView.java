package com.greenhouse.controller;

public record GreenhouseSettingsView(Boolean auto, Boolean voltage, Boolean heating, Float minHumidity, Float maxHumidity,
		Integer minTemperature) {

}
