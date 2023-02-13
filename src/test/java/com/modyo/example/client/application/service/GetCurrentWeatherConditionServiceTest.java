package com.modyo.example.client.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import com.modyo.example.client.application.port.out.LoadWeatherPort;
import com.modyo.example.client.domain.model.WeatherCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCurrentWeatherConditionServiceTest {

  @Mock
  LoadWeatherPort port;
  @InjectMocks
  GetCurrentWeatherConditionService useCase;
  WeatherCondition weatherCondition = new WeatherCondition();

  @BeforeEach
  void setUp() {
    given(port.loadCurrent(anyString())).willReturn(weatherCondition);
  }

  @Test
  void getWeatherConditionTest() {
    weatherCondition.setCityName("World");
    WeatherCondition response = useCase.getWeatherCondition("World");
    assertEquals("World", response.getCityName());
  }
}
