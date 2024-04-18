package com.greenhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhouse.model.GreenhouseDeviceLogEntity;

public interface GreenhouseDeviceLogRepository extends JpaRepository<GreenhouseDeviceLogEntity, Long>{ 

	GreenhouseDeviceLogEntity findFirstByOrderByDateFromDesc();
}
