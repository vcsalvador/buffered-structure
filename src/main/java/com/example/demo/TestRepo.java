package com.example.demo;

import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestRepo {

  void write(Collection<String> values) {
    log.info("{}", values);
  }
}
