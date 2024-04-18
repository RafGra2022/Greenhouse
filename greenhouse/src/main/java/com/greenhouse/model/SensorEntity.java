package com.greenhouse.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity(name = "sensordata")
@Data
public class SensorEntity {


	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,  generator = "sensor_seq")
	@SequenceGenerator(name = "sensor_seq", sequenceName = "sensor_seq", allocationSize = 1)
	private Long id;

	@Column(name="gndtemp")
	private Float groundTemperature;
	
	@Column(name ="airtemp")
	private Float airTemperature;
	
	@Column(name ="airhum")
	private Float airHumidity;
	
	@Column(name ="gndhum1")
	private Float groundHumidity1;
	
	@Column(name ="gndhum2")
	private Float groundHumidity2;
	
	@Column(name ="gndhum3")
	private Float groundHumidity3;
	
	@Column(name ="voltage")
	private Boolean voltage;
	
	@CreationTimestamp
	@Column(name = "insstmp")
	private LocalDateTime insstmp;
	
}
