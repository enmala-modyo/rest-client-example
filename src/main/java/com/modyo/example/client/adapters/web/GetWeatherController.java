package com.modyo.example.client.adapters.web;

import com.modyo.example.client.adapters.web.dto.WeatherDto;
import com.modyo.example.client.adapters.web.dto.WeatherDtoMapper;
import com.modyo.example.client.application.port.in.GetCurrentWeatherConditionQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"example"})
@RestController
@RequiredArgsConstructor
public class GetWeatherController {

  private final GetCurrentWeatherConditionQuery port;
  private final WeatherDtoMapper mapper;

  @ApiOperation(
      value = "getCurrentWeather",
      response = WeatherDto.class)
  @GetMapping(
      value = "/weather",
      produces = "application/json")
  public ResponseEntity<WeatherDto> getCurrentCityWeather(
      @RequestParam(value = "city", defaultValue = "Santiago", required = false) String cityName
  ) {
    return ResponseEntity.ok(mapper.toDto(port.getWeatherCondition(cityName)));
  }
}
