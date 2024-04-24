package com.greenhouse.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.greenhouse.controller.DeviceStatusRequest;
import com.greenhouse.dto.DayPowerUsage;
import com.greenhouse.dto.PowerUsage;
import com.greenhouse.exception.EmptyRequestGreenhouseException;
import com.greenhouse.exception.NotFoundInDatabaseGreenhouseException;
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
	public String logDevice(DeviceStatusRequest deviceStatusRequest) throws NotFoundInDatabaseGreenhouseException, EmptyRequestGreenhouseException {
		if(deviceStatusRequest == null) {
			throw new EmptyRequestGreenhouseException("Request with empty body");
		}
		if (deviceStatusRequest.deviceWorking()) {
			greenhouseDeviceLogRepository.save(GreenhouseDeviceLogMapper.logDeviceOn());
			return "device logged";
		}
		GreenhouseDeviceLogEntity greenhouseDeviceLog = greenhouseDeviceLogRepository.findFirstByOrderByDateFromDesc();
		if(greenhouseDeviceLog == null) {
			throw new NotFoundInDatabaseGreenhouseException("greenhouse device log entity is empty, try run request again with deviceWorking : true value");
		}
		greenhouseDeviceLog.setDateTo(LocalDateTime.now());
		greenhouseDeviceLog.setWorkTime(greenhouseDeviceLog.getDateFrom().until(greenhouseDeviceLog.getDateTo(), ChronoUnit.MINUTES));
		return "device logged";
	}

	@Transactional
	public PowerUsage getDevicePowerUsageReport() throws NotFoundInDatabaseGreenhouseException {
		LocalDateTime dateFrom = LocalDateTime.of(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1),LocalTime.MIN);
		LocalDateTime dateTo = LocalDateTime.of(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
				LocalDate.now().getDayOfMonth()),LocalTime.MIDNIGHT).plusDays(1);
		
		List<GreenhouseDeviceLogEntity> logs = greenhouseDeviceLogRepository.findByDateFromBetween(dateFrom, dateTo);
		if(logs == null) {
			throw new NotFoundInDatabaseGreenhouseException("Not found any record in that period of time");
		}
		
		List<DayPowerUsage> powerLogs =	logs.stream()
				.map(log-> {
					return new DayPowerUsage(log.getDateFrom().toLocalDate().toString(),log.getWorkTime()== null ? 0 : log.getWorkTime().intValue());})
				.toList();
		
		Map<String, Integer> powerLogsMap = powerLogs.stream()
				.collect(Collectors.groupingBy(powerLog -> powerLog.date(),
						 Collectors.summingInt(powerLog-> powerLog.workTime())));
		
		List<String> powerLogsSortedKey = powerLogsMap.keySet().stream().map(powerLog-> LocalDate.parse(powerLog)).sorted().map(powerLog -> powerLog.toString()).toList();
		
		return new PowerUsage(powerLogsSortedKey
				.stream()
				.map(powerLogKey-> {
					return new DayPowerUsage(powerLogKey, powerLogsMap.get(powerLogKey));})
				.toList());

	}
}
