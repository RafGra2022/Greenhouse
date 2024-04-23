package com.greenhouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.greenhouse.controller.DeviceStatus;
import com.greenhouse.controller.Sensor;
import com.greenhouse.controller.SensorSystemDataView;
import com.greenhouse.controller.SystemStatus;
import com.greenhouse.dto.SensorData;
import com.greenhouse.model.GreenhouseSensorMapper;
import com.greenhouse.model.SensorEntity;
import com.greenhouse.repository.SensorRepository;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class GreenhouseSensorServiceTest {

	@Mock
	private SensorRepository sensorRepository;
	@Mock
	private GreenhouseSensorMapper greenhouseSensorMapper;
	@Mock
	private List<SensorData> sensorDataList;
	
	@InjectMocks
	private GreenhouseSensorService greenhouseSensorService;
	
	private SensorEntity sensorEntity;

	@BeforeEach
	public void setup() {
		sensorEntity = new SensorEntity();
		sensorEntity.setId(1L);
		sensorEntity.setAirHumidity(60f);
		sensorEntity.setAirTemperature(15f);
		sensorEntity.setGroundHumidity1(55f);
		sensorEntity.setGroundHumidity2(43f);
		sensorEntity.setGroundTemperature(15f);
		sensorEntity.setInsstmp(LocalDateTime.now());
		sensorEntity.setGroundHumidity3(55f);
		sensorEntity.setVoltage(true);
	}
	
	@Test
	public void handleSensorDataTest() {
		SensorEntity sensorEntity = new SensorEntity();
		sensorEntity.setAirHumidity(60f);
		sensorEntity.setAirTemperature(15f);
		sensorEntity.setGroundHumidity1(55f);
		sensorEntity.setGroundHumidity2(43f);
		sensorEntity.setGroundTemperature(13f);
		Mockito.when(greenhouseSensorMapper.mapToSensorEntity(Mockito.any())).thenReturn( sensorEntity);
		assertEquals(null, greenhouseSensorService.handleSensorData(new SensorData(null, null, null, null, null, null, null)));
	}
	
	@Test
	public void lastDataTest() {
		Mockito.when(sensorRepository.findTopByOrderByIdDesc()).thenReturn(sensorEntity);
		Mockito.when(greenhouseSensorMapper.mapToSensorData(sensorEntity)).thenReturn(new SensorData(15f, 15f, 60f, 55f, 43f, 55f, true));
		Mockito.when(greenhouseSensorMapper.mapToSensorDataView(sensorEntity,new SystemStatus(new DeviceStatus("UP", true)))).thenReturn(new SensorSystemDataView(	new Sensor(15f, 25f, 40f, 22f, 40f, 55f), new SystemStatus(new DeviceStatus("UP", true))));
		assertEquals(new SensorSystemDataView(new Sensor(15f, 25f, 40f, 22f, 40f, 55f), new SystemStatus(new DeviceStatus("UP", true))), greenhouseSensorService.lastData());
	}
}