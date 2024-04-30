package com.greenhouse.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.greenhouse.controller.DeviceStatus;
import com.greenhouse.controller.ForecastMapper;
import com.greenhouse.controller.ForecastResponse;
import com.greenhouse.controller.SensorSystemDataView;
import com.greenhouse.controller.SunriseResponse;
import com.greenhouse.controller.SystemStatus;
import com.greenhouse.dto.ForecastComparator;
import com.greenhouse.dto.ForecastData;
import com.greenhouse.dto.SensorData;
import com.greenhouse.exception.NotFoundInDatabaseGreenhouseException;
import com.greenhouse.exception.NotProcessedGreenhouseException;
import com.greenhouse.model.GreenhouseSensorMapper;
import com.greenhouse.model.SensorEntity;
import com.greenhouse.repository.ForecastRepository;
import com.greenhouse.repository.SensorRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GreenhouseSensorService {

	private List<SensorData> sensorDataList = new ArrayList<>();
	private final SensorRepository sensorRepository;
	private final GreenhouseSensorMapper greenhouseSensorMapper;
	private final ForecastRepository forecastRepository;
	private final WebClient espGreenhouse;
	
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

	public List<ForecastResponse> forecast() {
		List<ForecastData> forecasts = ForecastMapper
				.mapToForecastData(forecastRepository.findByDateAfter(LocalDate.now().minusDays(1)));
		if(forecasts == null) {
			throw new NotFoundInDatabaseGreenhouseException("Empty forecast entity");
		}
		List<ForecastData> forecastsMutableData = forecasts.stream().map(o -> o).collect(Collectors.toList());

		Collections.sort(forecastsMutableData, new ForecastComparator());
		return ForecastMapper.mapToForecastView(forecastsMutableData);
	}

	
//	@Scheduled(cron = "0/1 0 * * ?")
	public Boolean notifyDeviceSunrise(String request) {
		boolean sunrise = isSunrise();
		if (sunrise) {
			espGreenhouse.put()
					.uri(uriBuilder -> uriBuilder.path("/sunrise").build())
					.body(BodyInserters.fromValue(new SunriseResponse(sunrise)))
					.retrieve()
					.onStatus(status -> status != HttpStatus.OK,
						status -> Mono.error(new NotProcessedGreenhouseException(
							"Something went wrong while communicate with esp8266")))
					.bodyToMono(String.class).block();
			return true;
		}
		return false;
	}

	public Boolean isSunrise() {
		LocalTime now = LocalTime.now();
		LocalTime sunrise = forecastRepository.findByDate(LocalDate.now()).getSunrise();
		LocalTime sunrisePlusHour = sunrise.plusHours(1);
		if(now.isAfter(sunrise) && now.isBefore(sunrisePlusHour)) {
			return true;
		}
		return false;
	}
}
