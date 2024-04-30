package com.greenhouse.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.greenhouse.controller.DeviceStatusRequest;
import com.greenhouse.exception.EmptyRequestGreenhouseException;
import com.greenhouse.exception.NotFoundInDatabaseGreenhouseException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GreenhouseLogServiceTest {

	@Autowired
	private GreenhouseLogService greenhouseLogService;
	
	@Test
	void logDeviceTest() throws NotFoundInDatabaseGreenhouseException, EmptyRequestGreenhouseException {
		DeviceStatusRequest deviceStatusRequest = new DeviceStatusRequest(false);
		greenhouseLogService.logDevice(deviceStatusRequest);
	}
}
