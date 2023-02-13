package com.modyo.example.client.adapters.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.modyo.example.client.adapters.web.dto.WeatherDto;
import com.modyo.example.client.adapters.web.dto.WeatherDtoMapper;
import com.modyo.example.client.application.port.in.GetCurrentWeatherConditionQuery;
import com.modyo.example.client.domain.model.WeatherCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetWeatherControllerTest {

  @Mock
  GetCurrentWeatherConditionQuery inputPort;
  @Mock
  WeatherDtoMapper mapper;

  @InjectMocks
  GetWeatherController controller;

  WeatherCondition weatherCondition = new WeatherCondition();

  @BeforeEach
  void setUp() {
    weatherCondition.setTemperature("10.1");
    given(inputPort.getWeatherCondition(anyString())).willReturn(weatherCondition);
  }

  @Test
  void getCurrentCityWeatherTest() {
    String name = "Test";
    weatherCondition.setCityName(name);
    var dto =  WeatherDto.builder().temperature("10.1").cityName(name).build();
    given(mapper.toDto(any())).willReturn(dto);
    ResponseEntity<WeatherDto> responseEntity =
        controller.getCurrentCityWeather(name);

    assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    assertEquals(name, responseEntity.getBody().getCityName());
  }
}
