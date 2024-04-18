package com.greenhouse.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ForecastData(	LocalDate date, 
		Integer highTemperature, 
		Integer lowTemperature, 
		Float precip,
		String phenomenom, 
		LocalTime sunrise, 
		LocalTime sunset) {

}
