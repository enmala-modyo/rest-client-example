package com.modyo.example.client.application.port.in;

import com.modyo.example.client.domain.model.WeatherCondition;

public interface GetCurrentWeatherConditionQuery {

  WeatherCondition getWeatherCondition(String cityName);
}
