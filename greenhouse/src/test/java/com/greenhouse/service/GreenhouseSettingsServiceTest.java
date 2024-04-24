package com.greenhouse.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GreenhouseSettingsServiceTest {

//	@Autowired
//	private GreenhouseSettingsService greenhouseSettingsService;
	
//	@Test
//	void notifyDeviceTest() {
//		GreenhouseSettingsRequest greenhouseSettingsRequest = new GreenhouseSettingsRequest(true, false, false, 40f, 50f,8 );
//		greenhouseSettingsService.notifyDevice(greenhouseSettingsRequest);
//	}
}
