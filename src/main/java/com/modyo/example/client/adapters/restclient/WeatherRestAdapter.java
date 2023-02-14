package com.modyo.example.client.adapters.restclient;

import com.modyo.example.client.application.port.out.LoadWeatherPort;
import com.modyo.example.client.domain.model.WeatherCondition;
import com.modyo.ms.commons.audit.aspect.ModyoAudit;
import com.modyo.ms.commons.audit.aspect.context.AuditSetContext;
import com.modyo.ms.commons.core.exceptions.CriticalBusinessErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherRestAdapter implements LoadWeatherPort {

  private final WeatherRestClient client;
  private final WeatherResponseMapper mapper;

  @Override
  @ModyoAudit(changeType = "external_call", event = "get current weather conditions", prefix = "rest_service")
  public WeatherCondition loadCurrent(String city) {
    AuditSetContext.setParentEntity(new WeatherCondition(), city);
    try {
      var response = client.getCurrentWeather(city);
      return mapper.toEntity(response.getBody());
    } catch (Exception e) {
      throw new CriticalBusinessErrorException("Unable to get current weather for " + city, "1001");
    }
  }

}

