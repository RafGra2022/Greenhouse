package com.greenhouse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.greenhouse.dto.PowerUsage;
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
	public ResponseEntity<String> saveSensorData(@RequestBody SensorDataRequest sensorDataRequest) {
		return ResponseEntity.ok(greenhouseService.handleSensorData(sensorDataMapper.mapToSensorData(sensorDataRequest)));
	}

	@GetMapping("/sensor")
	public SensorSystemDataView sensorData(){
		return greenhouseService.lastData();
	}

	@PostMapping("/settings")
	public ResponseEntity<String> saveSettings(@RequestBody GreenhouseSettingsRequest greenhouseSettingsRequest) {
		greenhouseSettingsService.notifyDeviceSettings(greenhouseSettingsRequest);
		return ResponseEntity.ok(greenhouseSettingsService.handleSensorSettings(greenhouseSettingsRequest));
	}

	@PostMapping("/log")
	public ResponseEntity<String> logDevice(@RequestBody DeviceStatusRequest deviceStatusRequest){
		log.info("device log : " + deviceStatusRequest.deviceWorking());
		return ResponseEntity.ok(greenhouseLogService.logDevice(deviceStatusRequest));
	}

	@GetMapping("/log")
	public ResponseEntity<PowerUsage> getDeviceLogs() {
		return ResponseEntity.ok(greenhouseLogService.getDevicePowerUsageReport());
	}

	@GetMapping("/settings")
	public GreenhouseSettingsResponse settings(){
		return greenhouseSettingsService.readSettings();
	}

	@GetMapping("/forecast")
	public List<ForecastResponse> forecast() {
		return greenhouseService.forecast();
	}

	@GetMapping("/sunrise")
	public SunriseResponse sunriseResponse() {
		return new SunriseResponse(greenhouseService.isSunrise());
	}

	@GetMapping("/time")
	public ServerTime time() {
		return timeKeeper.time();
	}
}
