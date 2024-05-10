package com.greenhouse.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.greenhouse.controller.DeviceStatus;
import com.greenhouse.controller.SensorSystemDataView;
import com.greenhouse.controller.SystemStatus;
import com.greenhouse.dto.SensorData;
import com.greenhouse.exception.NotFoundInDatabaseGreenhouseException;
import com.greenhouse.model.GreenhouseSensorMapper;
import com.greenhouse.model.SensorEntity;
import com.greenhouse.repository.SensorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GreenhouseSensorService {
	
	private final SensorRepository sensorRepository;
	private final GreenhouseSensorMapper greenhouseSensorMapper;
	
	private LocalDateTime lastUpDateTime;
	private List<SensorData> sensorDataList = new ArrayList<>();

	public String handleSensorData(SensorData sensorData) {
		lastUpDateTime = LocalDateTime.now();
		int batchSize = 1200;
		sensorDataList.add(sensorData);
		if (sensorDataList.size() == 1) {
			sensorRepository.save(greenhouseSensorMapper.mapToSensorEntity(sensorData));
		} else if (sensorDataList.size() >= batchSize) {
			sensorDataList = new ArrayList<>();
		}

		return null;
	}

	public SensorSystemDataView lastData(){
		SensorData sensorData = null;
		if (!sensorDataList.isEmpty()) {
			sensorData = sensorDataList.get(sensorDataList.size() - 1);
			return greenhouseSensorMapper.mapToSensorDataView(sensorData, checkSystem(sensorData));
		}
		SensorEntity sensorEntity = sensorRepository.findTopByOrderByIdDesc();
		if(sensorEntity == null) {
			throw new NotFoundInDatabaseGreenhouseException("Not found any entity in repository");
		}
		sensorData = greenhouseSensorMapper.mapToSensorData(sensorEntity);
		return greenhouseSensorMapper.mapToSensorDataView(sensorEntity, checkSystem(sensorData));
	}
	
	private SystemStatus checkSystem(SensorData sensorData) {
		SystemStatus status = new SystemStatus(new DeviceStatus("UP",sensorData.voltage()));
		if (ChronoUnit.SECONDS.between(lastUpDateTime != null ? lastUpDateTime : LocalDateTime.now(),
				LocalDateTime.now()) > 60L) {
			status = new SystemStatus(new DeviceStatus("DOWN",sensorData.voltage()));
		}
		return status;
	}
}
