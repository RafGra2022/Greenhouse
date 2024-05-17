package com.greenhouse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TimeKeeperTest {
	
	@Test
	public void checkTimeZone() {
		assertEquals(LocalDateTime.now(ZoneId.of("Europe/Warsaw")).getHour(), LocalDateTime.now().getHour());
	}
}
