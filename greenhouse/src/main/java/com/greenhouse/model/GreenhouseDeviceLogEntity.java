package com.greenhouse.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity(name = "greenhouselog")
@Data
public class GreenhouseDeviceLogEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,  generator = "greenhouselog_seq")
	@SequenceGenerator(name = "greenhouselog_seq", sequenceName = "greenhouselog_seq", allocationSize = 1)
	private Long id;
	
	@Column(name="datefrom")
	private LocalDateTime dateFrom;
	
	@Column(name="dateto")
	private LocalDateTime dateTo;
	
	@Column(name="work")
	private Long workTime;
	
}
