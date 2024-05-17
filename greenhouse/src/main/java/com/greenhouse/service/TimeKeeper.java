package com.greenhouse.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.greenhouse.controller.ServerTime;

@Service
public class TimeKeeper {

	public ServerTime time() {

		return new ServerTime(LocalDate.now().getDayOfMonth(), LocalTime.now().getHour(),
				LocalTime.now().getMinute());
	}

}
