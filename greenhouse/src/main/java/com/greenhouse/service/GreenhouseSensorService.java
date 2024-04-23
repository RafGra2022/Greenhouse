package com.greenhouse.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.greenhouse.controller.DeviceStatus;
import com.greenhouse.controller.ForecastMapper;
import com.greenhouse.controller.ForecastResponse;
import com.greenhouse.controller.SensorSystemDataView;
import com.greenhouse.controller.SystemStatus;
import com.greenhouse.dto.ForecastComparator;
import com.greenhouse.dto.ForecastData;
import com.greenhouse.dto.SensorData;
import com.greenhouse.model.GreenhouseSensorMapper;
import com.greenhouse.repository.ForecastRepository;
import com.greenhouse.repository.SensorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GreenhouseSensorService {

	private List<SensorData> sensorDataList = new ArrayList<>();
	private final SensorRepository sensorRepository;
	private final GreenhouseSensorMapper greenhouseSensorMapper;
	private final ForecastRepository forecastRepository;
	
	private LocalDateTime lastUpDateTime;

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

	public SensorSystemDataView lastData() {
		SensorData sensorData = null;
		if (!sensorDataList.isEmpty()) {
			sensorData = sensorDataList.get(sensorDataList.size() - 1);
			return greenhouseSensorMapper.mapToSensorDataView(sensorData, checkSystem(sensorData));
		}
		sensorData = greenhouseSensorMapper.mapToSensorData(sensorRepository.findTopByOrderByIdDesc());
		return greenhouseSensorMapper.mapToSensorDataView(sensorRepository.findTopByOrderByIdDesc(), checkSystem(sensorData));
	}
	
	private SystemStatus checkSystem(SensorData sensorData) {
		SystemStatus status = new SystemStatus(new DeviceStatus("UP",sensorData.voltage()));
		if (ChronoUnit.SECONDS.between(lastUpDateTime != null ? lastUpDateTime : LocalDateTime.now(),
				LocalDateTime.now()) > 60L) {
			status = new SystemStatus(new DeviceStatus("DOWN",sensorData.voltage()));
		}
		return status;
	}

	public List<ForecastResponse> forecast() {
		List<ForecastData> forecasts = ForecastMapper
				.mapToForecastData(forecastRepository.findByDateAfter(LocalDate.now().minusDays(1)));
		List<ForecastData> forecastsMutableData = forecasts.stream().map(o -> o).collect(Collectors.toList());

		Collections.sort(forecastsMutableData, new ForecastComparator());
		return ForecastMapper.mapToForecastView(forecastsMutableData);
	}



//	@Scheduled(cron = "0/1 0 * * ?")
	public void isSunrise() {
//		LocalTime now = LocalTime.now();
//		LocalTime sunrise = forecastRepository.findByDate(LocalDate.now()).getSunrise();
//		LocalTime sunrisePlusHour = sunrise.plusHours(1);
//		if(now.isAfter(sunrise) && now.isBefore(sunrisePlusHour)) {
//			return new Sunrise(true);
//		}
	}
}
