package com.modyo.example.client.application.service;

import com.modyo.example.client.application.port.in.GetCurrentWeatherConditionQuery;
import com.modyo.example.client.application.port.out.LoadWeatherPort;
import com.modyo.example.client.domain.model.WeatherCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetCurrentWeatherConditionService implements GetCurrentWeatherConditionQuery {

  private final LoadWeatherPort port;

  @Override
  public WeatherCondition getWeatherCondition(String cityName) {
    return port.loadCurrent(cityName);
  }
}
