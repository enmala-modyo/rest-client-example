package com.modyo.example.client.adapters.restclient;

import com.modyo.example.client.application.port.out.OutputPort;
import org.springframework.stereotype.Service;

@Service
public class RestAdapter implements OutputPort {

  @Override
  public String loadGreeting(int hour) {
    String greeting;
    if (hour > 5 && hour < 12) {
      greeting = "Good Morning";
    } else if (hour < 20) {
      greeting = "Good Afternoon";
    } else {
      greeting = "Good Night";
    }
    return greeting;
  }
}
