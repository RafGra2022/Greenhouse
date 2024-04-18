package com.greenhouse.model;

import org.springframework.stereotype.Component;

import com.greenhouse.controller.Sensor;
import com.greenhouse.controller.SensorSystemDataView;
import com.greenhouse.controller.SystemStatus;
import com.greenhouse.dto.SensorData;

@Component
public class GreenhouseSensorMapper {

	public SensorEntity mapToSensorEntity(SensorData sensorData) {
		SensorEntity sensorEntity = new SensorEntity();

		sensorEntity.setAirHumidity(sensorData.airHumidity());
		sensorEntity.setAirTemperature(sensorData.airTemperature());
		sensorEntity.setGroundTemperature(sensorData.groundTemperature());
		sensorEntity.setGroundHumidity1(sensorData.groundHumidity1());
		sensorEntity.setGroundHumidity2(sensorData.groundHumidity2());
		sensorEntity.setGroundHumidity3(sensorData.groundHumidity3());
		sensorEntity.setVoltage(sensorData.voltage());

		return sensorEntity;
	}

	public SensorSystemDataView mapToSensorDataView(SensorData sensorData, SystemStatus status) {
		return new SensorSystemDataView(
				new Sensor(sensorData.groundTemperature(), sensorData.airTemperature(), sensorData.airHumidity(),
						sensorData.groundHumidity1(), sensorData.groundHumidity2(), sensorData.groundHumidity3()),status);

	}

	public SensorSystemDataView mapToSensorDataView(SensorEntity sensorEntity, SystemStatus status) {
		return new SensorSystemDataView(new Sensor(sensorEntity.getGroundTemperature(), sensorEntity.getAirTemperature(),
				sensorEntity.getAirHumidity(), sensorEntity.getGroundHumidity1(), sensorEntity.getGroundHumidity2(),
				sensorEntity.getGroundHumidity3()), status);
	}

	public SensorData mapToSensorData(SensorEntity sensorEntity) {
		return new SensorData(sensorEntity.getGroundTemperature(), sensorEntity.getAirTemperature(),
				sensorEntity.getAirHumidity(), sensorEntity.getGroundHumidity1(), sensorEntity.getGroundHumidity2(),
				sensorEntity.getGroundHumidity3(), sensorEntity.getVoltage());
	}

}
