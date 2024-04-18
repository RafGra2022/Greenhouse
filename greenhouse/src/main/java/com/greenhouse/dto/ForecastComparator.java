package com.greenhouse.dto;

import java.util.Comparator;

public class ForecastComparator implements Comparator<ForecastData>{

	@Override
	public int compare(ForecastData o1, ForecastData o2) {
		
		return o1.date().compareTo(o2.date());
	}

}
