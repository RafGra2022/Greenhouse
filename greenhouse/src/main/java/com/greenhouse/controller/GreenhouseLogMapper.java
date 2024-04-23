package com.greenhouse.controller;

import java.util.List;

import com.greenhouse.dto.DayPowerUsage;
import com.greenhouse.dto.PowerUsage;
import com.greenhouse.model.GreenhouseDeviceLogEntity;

public class GreenhouseLogMapper {

	public static PowerUsage mapToPowerUsage(List<GreenhouseDeviceLogEntity> logs) {
		
		return new PowerUsage(logs
				.stream()
				.map(log-> {
					return new DayPowerUsage(log.getDateFrom().toLocalDate().toString(),log.getWorkTime().intValue());
				}).toList());
		
	}
}
