package com.modyo.example.client.adapters.restclient;

import com.modyo.example.client.application.port.out.LoadWeatherPort;
import com.modyo.example.client.domain.model.WeatherCondition;
import com.modyo.ms.commons.core.exceptions.CriticalBusinessErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherRestAdapter implements LoadWeatherPort {

  private final WeatherRestClient client;
  private final WeatherResponseMapper mapper;

  @Override
  public WeatherCondition loadCurrent(String city) {
    try {
      var response = client.getCurrentWeather(city);
      return mapper.toEntity(response.getBody());
    } catch (Exception e) {
      throw new CriticalBusinessErrorException("Unable to get current weather for " + city, "1001");
    }
  }

}

