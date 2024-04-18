package com.greenhouse.model;

import java.time.LocalDateTime;

public class GreenhouseDeviceLogMapper {

	public static GreenhouseDeviceLogEntity logDeviceOn() {
		
		var greenhouseDeviceLogEntity = new GreenhouseDeviceLogEntity();
		
		greenhouseDeviceLogEntity.setDateFrom(LocalDateTime.now());
		
		return greenhouseDeviceLogEntity;
	}
}
