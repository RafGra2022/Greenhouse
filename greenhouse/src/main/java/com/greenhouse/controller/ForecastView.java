package com.greenhouse.controller;

public record ForecastView(
		String date, 
		Integer highTemperature, 
		Integer lowTemperature, 
		Float precip,
		String phenomenom, 
		String sunrise, 
		String sunset
		
) {} 
