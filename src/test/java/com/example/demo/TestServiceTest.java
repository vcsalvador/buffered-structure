package com.example.demo;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Slf4j
@ExtendWith(SpringExtension.class)
class TestServiceTest {

  private final Clock clock = Clock.systemUTC();

  private final TestBufferStructure testBufferStructure = new TestBufferStructure(new TestRepo());
  private TestService testService = new TestService(testBufferStructure);

  @Test
  void write() {
    IntStream.range(1, 10)
        .parallel()
        .forEach(
            part -> {
              Instant start = clock.instant();
              new Random().ints(1000, 0, 9999).forEach(r -> testService.write("" + r * part));
              log.info("Spent {}ms", Duration.between(start, clock.instant()).getNano() / 1e6);
            });
  }

  @Test
  void writeSync() {
    IntStream.range(1, 10)
        .parallel()
        .forEach(
            part -> {
              Instant start = clock.instant();
              new Random().ints(1000, 0, 9999).forEach(r -> testService.writeSync("" + r * part));
              log.info("Spent {}ms", Duration.between(start, clock.instant()).getNano() / 1e6);
            });
  }
}
