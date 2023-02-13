package com.modyo.example.client.domain.model;

import lombok.Data;

@Data
public class WeatherCondition {

    private String cityName;
    private String temperature;
    private String time;
    private String condition;


}

