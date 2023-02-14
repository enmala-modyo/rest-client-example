package com.modyo.example.client.config;

import com.modyo.example.client.adapters.restclient.WeatherRestClient;
import com.modyo.example.client.log.LogCapture;
import com.modyo.example.client.log.LogCaptureExtension;
import feign.Feign;
import feign.Logger.Level;
import feign.Request;
import feign.Request.HttpMethod;
import feign.RequestTemplate;
import feign.Response;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(LogCaptureExtension.class)
class CustomFeignLoggerTest {

  private final CustomFeignLogger feignLogger = new CustomFeignLogger();

  @BeforeEach
  void setUp() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setAttribute("requestId", "");
    request.setAttribute("correlationId", "");
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
  }

  @Test
  void logRequest(LogCapture logCapture) {
    var request = Request.create(HttpMethod.GET, "http://test/", Map.of(), "1".getBytes(), Charset.defaultCharset(),
        new RequestTemplate());
    feignLogger.logRequest(Feign.configKey(WeatherRestClient.class, WeatherRestClient.class.getMethods()[0]),
        Level.NONE, request);
    Assertions.assertTrue(logCapture.getLoggingEvents().get(0).getFormattedMessage().contains("http://test/"));
  }

  @Test
  void logRequestWithoutBody(LogCapture logCapture) {
    var request = Request.create(HttpMethod.GET, "http://test/", Map.of(), null, Charset.defaultCharset(),
        new RequestTemplate());
    feignLogger.logRequest(Feign.configKey(WeatherRestClient.class, WeatherRestClient.class.getMethods()[0]),
        Level.NONE, request);
    Assertions.assertTrue(logCapture.getLoggingEvents().get(0).getFormattedMessage().contains("(0-byte body)"));
  }

  @Test
  void logRequestWithoutCharset(LogCapture logCapture) {
    var request = Request.create(HttpMethod.GET, "http://test/", Map.of(), null, null, new RequestTemplate());
    feignLogger.logRequest(Feign.configKey(WeatherRestClient.class, WeatherRestClient.class.getMethods()[0]),
        Level.NONE, request);
    Assertions.assertTrue(logCapture.getLoggingEvents().get(0).getFormattedMessage().contains("(0-byte body)"));
  }

  @Test
  void logAndRebufferResponse(LogCapture logCapture) throws IOException {
    var request = Request.create(HttpMethod.GET, "http://test/", Map.of(), "1".getBytes(), Charset.defaultCharset(),
        new RequestTemplate());
    var response = Response.builder().status(200).reason("OK")
        .headers(Map.of()).body("body".getBytes()).request(request).build();
    feignLogger.logAndRebufferResponse(
        Feign.configKey(WeatherRestClient.class, WeatherRestClient.class.getMethods()[0]),
        Level.FULL, response, 10);
    Assertions.assertEquals(2, logCapture.getLoggingEvents().size());
    Assertions.assertTrue(logCapture.getLoggingEventAt(1).getFormattedMessage().contains("Response body: body"));
  }

  @Test
  void logAndRebufferResponseWithoutCharset(LogCapture logCapture) throws IOException {
    var request = Request.create(HttpMethod.GET, "http://test/", Map.of(), "1".getBytes(), Charset.defaultCharset(),
        new RequestTemplate());
    var response = Response.builder().status(200).reason("OK")
        .headers(Map.of()).body(null, (Charset) null).request(request).build();
    feignLogger.logAndRebufferResponse(
        Feign.configKey(WeatherRestClient.class, WeatherRestClient.class.getMethods()[0]),
        Level.FULL, response, 10);
    Assertions.assertEquals(2, logCapture.getLoggingEvents().size());
    Assertions.assertTrue(logCapture.getLoggingEventAt(1).getFormattedMessage().contains("Response body: null"));
  }

}
