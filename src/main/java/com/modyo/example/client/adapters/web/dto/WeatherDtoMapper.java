package com.modyo.example.client.adapters.web.dto;

import com.modyo.example.client.domain.model.WeatherCondition;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WeatherDtoMapper {
  WeatherDto toDto(WeatherCondition entity);
}
