package com.greenhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhouse.model.GreenhouseSettingsEntity;

public interface GreenhouseSettingsRepository extends JpaRepository<GreenhouseSettingsEntity, Long>{

	GreenhouseSettingsEntity findTopByOrderByIdDesc();
}
