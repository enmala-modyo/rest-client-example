package com.modyo.example.client.adapters.restclient;

import com.modyo.example.client.adapters.restclient.data.WeatherResponse;
import com.modyo.example.client.domain.model.WeatherCondition;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WeatherResponseMapper {
  @Mapping(source = "current.temperature", target = "temperature")
  @Mapping(source = "location.name", target = "cityName")
  @Mapping(source = "current.lastUpdated", target = "time")
  @Mapping(source = "current.condition.text", target = "condition")
  WeatherCondition toEntity(WeatherResponse restEntity);
}
