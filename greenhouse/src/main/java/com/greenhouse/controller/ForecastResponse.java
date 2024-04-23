package com.greenhouse.controller;

public record ForecastResponse(
		String date, 
		Integer highTemperature, 
		Integer lowTemperature, 
		Float precip,
		String phenomenom, 
		String sunrise, 
		String sunset
		
) {} 
