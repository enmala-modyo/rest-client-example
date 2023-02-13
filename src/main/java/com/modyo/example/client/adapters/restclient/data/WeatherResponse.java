package com.modyo.example.client.adapters.restclient.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherResponse {
  private Location location;
  private Weather current;

  @Data
  public static class Location {
    private String name;
    private String region;
    private String country;
    private float lat;
    private float lon;
    @JsonProperty("tz_id")
    private String timeZoneId;
    private String localtime;
  }

  @Data
  public static class Weather {
    @JsonProperty("last_updated")
    private String lastUpdated;
    @JsonProperty("temp_c")
    private float temperature;
    private Condition condition;

  }

  @Data
  public static class Condition {
    private String text;
    private String icon;
    private int code;
  }
}
