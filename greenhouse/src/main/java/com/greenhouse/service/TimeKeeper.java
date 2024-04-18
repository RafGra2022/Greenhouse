package com.greenhouse.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

import com.greenhouse.controller.ServerTime;

@Service
public class TimeKeeper {

	public ServerTime time() {

		return new ServerTime(LocalDate.now().getDayOfMonth(), LocalTime.now(ZoneId.of("Europe/Warsaw")).getHour(),
				LocalTime.now(ZoneId.of("Europe/Warsaw")).getMinute());
	}

}
