package com.greenhouse.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity(name = "forecast")
@Data
public class ForecastEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "forecast_seq")
	@SequenceGenerator(name = "forecast_seq", sequenceName = "forecast_seq", allocationSize = 1)
	private Long id;

	@Column(name = "wdate")
	private LocalDate date;

	@Column(name = "hightemp")
	private Integer highTemperature;

	@Column(name = "lowtemp")
	private Integer lowTemperature;

	@Column(name = "precip")
	private Float precipitationValue;

	@Column(name = "phenomen")
	private String phenomenon;

	@Column(name = "sunrise")
	private LocalTime sunrise;

	@Column(name = "sunset")
	private LocalTime sunset;
	
	@UpdateTimestamp
	private LocalDateTime updstmp;
}
