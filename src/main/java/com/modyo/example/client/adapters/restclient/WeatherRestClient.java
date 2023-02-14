package com.modyo.example.client.adapters.restclient;

import com.modyo.example.client.adapters.restclient.data.WeatherResponse;
import com.modyo.example.client.config.WeatherFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "weatherClient", url = "${datasources.rest.webServices.weather.base-url}", configuration = {
    WeatherFeignClientConfig.class})
public interface WeatherRestClient {

  @GetMapping(value = "/current.json")
  ResponseEntity<WeatherResponse> getCurrentWeather(@RequestParam("q") String city);
}
