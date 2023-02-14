package com.modyo.example.client.adapters.restclient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.modyo.example.client.domain.model.WeatherCondition;
import com.modyo.ms.commons.core.exceptions.CriticalBusinessErrorException;
import feign.FeignException;
import feign.Request;
import feign.RequestTemplate;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


class WeatherRestAdapterTest {

  WeatherRestClient restClient = mock(WeatherRestClient.class);
  WeatherResponseMapper mapper = mock(WeatherResponseMapper.class);

  WeatherRestAdapter restAdapter = new WeatherRestAdapter(restClient, mapper);
  WeatherCondition weatherCondition = new WeatherCondition();

  @BeforeEach
  void setUp() {
    weatherCondition.setCondition("Sunny");
    weatherCondition.setTemperature("30.1");
    given(mapper.toEntity(any())).willReturn(weatherCondition);
    given(restClient.getCurrentWeather(anyString())).willReturn(ResponseEntity.ok().build());
  }

  @Test
  void testLoadCurrentReturnsData() {
    weatherCondition.setCityName("Nairobi");
    var response = restAdapter.loadCurrent("Nairobi");
    assertEquals(weatherCondition, response);
  }

  @Test
  void testLoadCurrentDoesNotReturnData() {
    Request fakeRequest = Request.create(Request.HttpMethod.GET, "url",
        new HashMap<>(), null, new RequestTemplate());
    given(restClient.getCurrentWeather(anyString())).willThrow(
        new FeignException.NotFound("Not Found", fakeRequest, null, null));
    assertThrows(CriticalBusinessErrorException.class, () -> restAdapter.loadCurrent("any"));
  }
}
