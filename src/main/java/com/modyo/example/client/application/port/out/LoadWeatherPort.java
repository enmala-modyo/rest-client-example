package com.modyo.example.client.application.port.out;

import com.modyo.example.client.domain.model.WeatherCondition;

public interface LoadWeatherPort {

  WeatherCondition loadCurrent(String city);
}
