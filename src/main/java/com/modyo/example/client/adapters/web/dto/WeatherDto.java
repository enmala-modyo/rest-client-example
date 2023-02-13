package com.modyo.example.client.adapters.web.dto;

import com.modyo.ms.commons.core.dtos.Dto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WeatherDto extends Dto {

  private String cityName;
  private String temperature;
  private String time;
  private String condition;

}
