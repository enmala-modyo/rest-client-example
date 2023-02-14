package com.modyo.example.client.config;

import feign.Logger;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Slf4j
public class WeatherFeignClientConfig {

  @Value("${datasources.rest.webServices.weather.api-key}")
  private String apiKey;

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      log.info("Add API Key query param");
      requestTemplate.query("key", apiKey);
      var body = requestTemplate.body() != null ? new String(requestTemplate.body()) : null;
      log.info("Body:" + body);
      log.info("Headers:" + requestTemplate.headers());
      log.info("URL:" + requestTemplate.url());
    };
  }

  @Bean
  public Logger requestAndResponseCustomLogger() {
    return new CustomFeignLogger();
  }
}
