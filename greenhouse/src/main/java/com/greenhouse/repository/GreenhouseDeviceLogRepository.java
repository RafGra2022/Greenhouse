package com.greenhouse.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhouse.model.GreenhouseDeviceLogEntity;

public interface GreenhouseDeviceLogRepository extends JpaRepository<GreenhouseDeviceLogEntity, Long>{ 

	GreenhouseDeviceLogEntity findFirstByOrderByDateFromDesc();
	
	List<GreenhouseDeviceLogEntity> findByDateFromBetween(LocalDateTime dateFrom, LocalDateTime dateTo);
}
