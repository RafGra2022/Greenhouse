package com.greenhouse.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.greenhouse.controller.DeviceStatusView;
import com.greenhouse.model.GreenhouseDeviceLogEntity;
import com.greenhouse.model.GreenhouseDeviceLogMapper;
import com.greenhouse.repository.GreenhouseDeviceLogRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GreenhouseLogService {

	private final GreenhouseDeviceLogRepository greenhouseDeviceLogRepository;
	
	@Transactional
	public String logDevice(DeviceStatusView deviceStatusView) {
		if (deviceStatusView.deviceWorking()) {
			greenhouseDeviceLogRepository.save(GreenhouseDeviceLogMapper.logDeviceOn());
			return "device logged";
		}
		GreenhouseDeviceLogEntity greenhouseDeviceLog = greenhouseDeviceLogRepository.findFirstByOrderByDateFromDesc();
		greenhouseDeviceLog.setDateTo(LocalDateTime.now());
		greenhouseDeviceLog.setWorkTime(greenhouseDeviceLog.getDateFrom().until(greenhouseDeviceLog.getDateTo(), ChronoUnit.MINUTES));
		return "device logged";
	}
}
