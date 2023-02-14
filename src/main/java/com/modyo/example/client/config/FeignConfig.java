package com.modyo.example.client.config;

import feign.Logger;
import feign.Logger.Level;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
  @Bean
  Logger.Level feignLoggerLevel() {
    return Level.BASIC;
  }
}
