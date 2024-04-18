package com.greenhouse.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity(name = "greenhousesettings")
@Data
public class GreenhouseSettingsEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,  generator = "greenhousesettings_seq")
	@SequenceGenerator(name = "greenhousesettings_seq", sequenceName = "greenhousesettings_seq", allocationSize = 1)
	private Long id;

	@Column(name="auto")
	private Boolean auto;

	@Column(name="voltage")
	private Boolean voltage;

	@Column(name="heating")
	private Boolean heating;

	@Column(name="minhum")
	private Float minHumidity;

	@Column(name="maxhum")
	private Float maxHumidity;

	@Column(name="mintemp")
	private Integer minTemperature;
}
