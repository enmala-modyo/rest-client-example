package com.modyo.example.client.config;

import com.modyo.ms.commons.audit.aspect.context.AuditGetContext;
import com.modyo.ms.commons.audit.aspect.context.AuditSetContext;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFeignLogger extends Logger {
  private static final String AUDIT_PREFIX = "rest_service";

  @Override
  protected void logRequest(String configKey, Level logLevel, Request request) {
    int bodyLength = 0;
    if (request.body() != null) {
      bodyLength = request.length();
    }
    String bodyText =
        request.charset() != null && bodyLength > 0 ? new String(request.body(), request.charset()) : null;
    log.info("---> {} {} HTTP/1.1 ({}-byte body) \n {}", request.httpMethod().name(), request.url(), bodyLength,
        bodyText);
    log(configKey, "---> %s %s HTTP/1.1 (%s-byte body) ", request.httpMethod().name(), request.url(), bodyLength);
  }

  @Override
  protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime)
      throws IOException {
    int status = response.status();
    Request request = response.request();
    String bodyText = null;
    byte[] bodyBytes = {};
    if (response.body() != null) {
      bodyText = new String(Util.toByteArray(response.body().asInputStream()), response.charset());
      bodyBytes = bodyText.getBytes(response.charset());
    }
    var requestString = String.format("%s %s HTTP/1.1 %s (%s) ", request.httpMethod().name(), request.url(), status, elapsedTime);
    log.info("<--- {}", requestString);
    log.info("Response body: {}", bodyText);
    AuditSetContext.setInitialEntityInfo(AUDIT_PREFIX, requestString, AuditGetContext.getParentEntityId());
    AuditSetContext.setNewValue(AUDIT_PREFIX, bodyText);
    log(configKey, "<--- %s %s HTTP/1.1 %s (%sms) ", request.httpMethod().name(), request.url(), status, elapsedTime);
    return Response.builder().status(response.status()).reason(response.reason())
        .headers(response.headers()).body(bodyBytes).request(request).build();
  }


  @Override
  protected void log(String configKey, String format, Object... args) {
    log.debug(format(configKey, format, args));
  }

  protected String format(String configKey, String format, Object... args) {
    var template = methodTag(configKey) + format;
    return String.format(template, args);
  }
}

