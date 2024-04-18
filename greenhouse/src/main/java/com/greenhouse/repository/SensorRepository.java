package com.greenhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhouse.model.SensorEntity;

public interface SensorRepository extends JpaRepository< SensorEntity, Long>{
	
	SensorEntity findTopByOrderByIdDesc();

}
