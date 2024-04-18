package com.greenhouse.dto;

public record SensorData(Float groundTemperature, Float airTemperature, Float airHumidity, Float groundHumidity1,
		Float groundHumidity2, Float groundHumidity3, Boolean voltage){

}
