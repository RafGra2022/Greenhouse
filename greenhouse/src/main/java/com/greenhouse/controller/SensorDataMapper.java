package com.greenhouse.controller;

import org.springframework.stereotype.Component;

import com.greenhouse.dto.SensorData;

@Component
public class SensorDataMapper {

	SensorData mapToSensorData(SensorDataRequest sensorDataRequest) {

		return new SensorData(sensorDataRequest.sensors().groundTemperature(), sensorDataRequest.sensors().airTemperature(),
				sensorDataRequest.sensors().airHumidity(), sensorDataRequest.sensors().groundHumidity1(),
				sensorDataRequest.sensors().groundHumidity2(), sensorDataRequest.sensors().groundHumidity3(),sensorDataRequest.deviceStatus().voltage());
	}


}
