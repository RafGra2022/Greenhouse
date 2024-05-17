package com.greenhouse.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.greenhouse.controller.ForecastMapper;
import com.greenhouse.controller.ForecastResponse;
import com.greenhouse.controller.SunriseResponse;
import com.greenhouse.dto.ForecastComparator;
import com.greenhouse.dto.ForecastData;
import com.greenhouse.exception.NotFoundInDatabaseGreenhouseException;
import com.greenhouse.exception.NotProcessedGreenhouseException;
import com.greenhouse.model.ForecastEntity;
import com.greenhouse.repository.ForecastRepository;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Setter
@Slf4j
public class ForecastService {
	
	private final ForecastRepository forecastRepository;
	private final WebClient espGreenhouse;
	
	private ForecastEntity forecastEntity;
	private Boolean sunrise = false;

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
	
	@Scheduled(cron = " 0 */1 * ? * *")
	public Boolean notifyDeviceSunrise() {
		boolean sunrise = isSunrise();
		if (sunrise != this.sunrise) {
			this.sunrise = sunrise;
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

	@Scheduled(cron = " 0 0 2 */1 * *")
	public void forecastCheck() {
		log.info("sunrise time check");
		forecastEntity = forecastRepository.findByDate(LocalDate.now());
	}
	
	public Boolean isSunrise() {
		LocalTime now = LocalTime.now();
		if(forecastEntity == null) {
			return false;
		}
		LocalTime sunrise = forecastEntity.getSunrise();
		LocalTime sunrisePlusHour = sunrise.plusHours(1);
		if(now.isAfter(sunrise) && now.isBefore(sunrisePlusHour)) {
			return true;
		}
		return false;
	}
}
