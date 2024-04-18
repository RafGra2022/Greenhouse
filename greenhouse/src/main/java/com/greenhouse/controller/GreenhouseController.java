package com.greenhouse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenhouse.service.GreenhouseLogService;
import com.greenhouse.service.GreenhouseSensorService;
import com.greenhouse.service.GreenhouseSettingsService;
import com.greenhouse.service.TimeKeeper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/greenhouse")
@RequiredArgsConstructor
@Slf4j
public class GreenhouseController {

	private final GreenhouseSensorService greenhouseService;
	private final GreenhouseLogService greenhouseLogService;
	private final GreenhouseSettingsService greenhouseSettingsService;
	private final SensorDataMapper sensorDataMapper;
	private final TimeKeeper timeKeeper;

	@PostMapping("/sensor")
	public ResponseEntity<String> saveSensorData(@RequestBody SensorDataView sensorDataView) {
		return ResponseEntity.ok(greenhouseService.handleSensorData(sensorDataMapper.mapToSensorData(sensorDataView)));
	}

	@GetMapping("/sensor")
	public SensorSystemDataView sensorData() {
		return greenhouseService.lastData();
	}

	@PostMapping("/settings")
	public ResponseEntity<String> saveSettings(@RequestBody GreenhouseSettingsView greenhouseSettingsView) {
		greenhouseSettingsService.notifyDevice(greenhouseSettingsView);
		return ResponseEntity.ok(greenhouseSettingsService.handleSensorSettings(greenhouseSettingsView));
	}

	@PostMapping("/log")
	public ResponseEntity<String> logDevice(@RequestBody DeviceStatusView deviceStatusView) {
		log.info("device log : " + deviceStatusView.deviceWorking());
		return ResponseEntity.ok(greenhouseLogService.logDevice(deviceStatusView));
	}

	@GetMapping("/settings")
	public GreenhouseSettingsView settings(){
		return greenhouseSettingsService.readSettings();
	}

	@GetMapping("/forecast")
	public List<ForecastView> forecast() {
		return greenhouseService.forecast();
	}

	@GetMapping("/sunrise")
	public Sunrise sunrise() {
		return new Sunrise(false);
	}

	@GetMapping("/time")
	public ServerTime time() {
		return timeKeeper.time();
	}
}
