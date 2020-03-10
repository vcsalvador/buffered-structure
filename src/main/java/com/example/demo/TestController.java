package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TestController {

  private final TestService testService;

  public TestController(TestService testService) {
    this.testService = testService;
  }

  @PostMapping
  ResponseEntity testPost(@RequestBody String message) {
    testService.write(message);
    return ResponseEntity.ok("It works: " + message);
  }

}
