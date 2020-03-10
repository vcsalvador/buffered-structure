package com.example.demo;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class TestService {

  private final TestBufferStructure testBufferStructure;

  public TestService(TestBufferStructure testBufferStructure) {
    this.testBufferStructure = testBufferStructure;
  }

  void write(final String message) {
    testBufferStructure.add(ThreadLocalRandom.current().nextInt(0,Integer.MAX_VALUE), message);
  }

  void writeSync(final String message) {
    testBufferStructure.addSynchronized(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE), message);
  }
}
