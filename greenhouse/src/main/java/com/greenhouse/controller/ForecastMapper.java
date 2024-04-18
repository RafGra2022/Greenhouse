package com.greenhouse.controller;

import java.util.List;

import com.greenhouse.dto.ForecastData;
import com.greenhouse.model.ForecastEntity;

public class ForecastMapper {

	public static List<ForecastView> mapToForecastView(List<ForecastData> forecasts) {

		return forecasts
				.stream()
				.map(forecast->{
			return new ForecastView(forecast.date().toString(), forecast.highTemperature(),
					forecast.lowTemperature(), forecast.precip(),
					forecast.phenomenom(), forecast.sunrise().toString(), forecast.sunset().toString()); 
				}).toList();
		
	}
	
	public static List<ForecastData> mapToForecastData(List<ForecastEntity> forecasts) {

		return forecasts
				.stream()
				.map(forecast->{
			return new ForecastData(forecast.getDate(), forecast.getHighTemperature(),
					forecast.getLowTemperature(), forecast.getPrecipitationValue(),
					forecast.getPhenomenon(), forecast.getSunrise(), forecast.getSunset()); 
				}).toList();
		
	}
}
