package com.greenhouse.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greenhouse.model.ForecastEntity;


public interface ForecastRepository extends JpaRepository<ForecastEntity, Long>{

	List<ForecastEntity> findByDateAfter( LocalDate dateFrom);
	
	ForecastEntity findByDate(LocalDate date);
	
	ForecastEntity findTopByOrderByDateDesc();
}
