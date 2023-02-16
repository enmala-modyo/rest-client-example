package com.modyo.example.client.adapters.web;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modyo.example.client.adapters.web.dto.WeatherDto;
import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherIntegrationTest {

  private static ClientAndServer mockServer;
  @Autowired
  private MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();

  WeatherDto expectedResponse = WeatherDto.builder()
      .cityName("Santiago")
      .condition("Sunny")
      .temperature("20.4")
      .time("2023-02-01 11:00")
      .build();


  @BeforeAll
  public static void startMockServer() {
    mockServer = startClientAndServer(1080);
  }

  @AfterAll
  public static void stopMockServer() {
    mockServer.stop();
  }

  @Test
  void givenACityName_WhenWeatherReq_ThenVerifyRetrived() throws Exception {
    createExpectationForGetCurrentWeatherCondition();
    mockMvc.perform(get("/weather")
            .param("city", "Santiago"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
  }

  @Test
  void givenANotExistentCityName_WhenWeatherReq_ThenVerifyError() throws Exception {
    createExpectationForNoLocationFound();
    mockMvc.perform(get("/weather")
            .param("city", "No City"))
        .andDo(print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().json(new String(
            this.getClass().getResourceAsStream("/responses/errorNoData.json").readAllBytes())));
  }

  private void createExpectationForGetCurrentWeatherCondition() throws IOException {
    InputStream is = this.getClass().getResourceAsStream("/responses/currentWeather.json");
    String responseBody = new String(is.readAllBytes());
    mockServer
        .when(
            request()
                .withMethod("GET")
                .withPath("/current.json"), Times.exactly(1)
        ).respond(
            response()
                .withStatusCode(200)
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody(responseBody)
        );
  }

  private void createExpectationForNoLocationFound() throws IOException {
    InputStream is = this.getClass().getResourceAsStream("/responses/noLocationFound.json");
    String responseBody = new String(is.readAllBytes());
    mockServer
        .when(
            request()
                .withMethod("GET")
                .withPath("/current.json"), Times.exactly(1)
        ).respond(
            response()
                .withStatusCode(400)
                .withContentType(MediaType.APPLICATION_JSON)
                .withBody(responseBody)
        );
  }
}
