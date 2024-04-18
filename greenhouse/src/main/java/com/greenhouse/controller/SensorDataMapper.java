package com.greenhouse.controller;

import org.springframework.stereotype.Component;

import com.greenhouse.dto.SensorData;

@Component
public class SensorDataMapper {

	SensorData mapToSensorData(SensorDataView sensorDataView) {

		return new SensorData(sensorDataView.sensors().groundTemperature(), sensorDataView.sensors().airTemperature(),
				sensorDataView.sensors().airHumidity(), sensorDataView.sensors().groundHumidity1(),
				sensorDataView.sensors().groundHumidity2(), sensorDataView.sensors().groundHumidity3(),sensorDataView.deviceStatus().voltage());
	}


}
