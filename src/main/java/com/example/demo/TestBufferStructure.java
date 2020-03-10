package com.example.demo;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestBufferStructure {

  private static final int MAX_SIZE = 10;
  private final ConcurrentHashMap<Integer, String> bufferMap;
  private final TestRepo testRepo;

  TestBufferStructure(TestRepo testRepo) {
    this.testRepo = testRepo;
    bufferMap = new ConcurrentHashMap<>(MAX_SIZE * 100);
    new Timer().scheduleAtFixedRate(this.dispatch(), 80L, 80L);
  }

  void add(final int key, final String message) {
    log.info("putting object");
    bufferMap.put(key, message);
    if (bufferMap.size() >= MAX_SIZE) {
      log.info("trying to buffer-out {} elements", bufferMap.size());
      synchronized (bufferMap.entrySet()) {
        log.info("buffering-out {} elements", bufferMap.size());
        if (bufferMap.size() >= MAX_SIZE) {
          testRepo.write(bufferMap.values());
          log.info("clearing {} elements", bufferMap.size());
          bufferMap.clear();
        } else {
          log.info("Couldn't buffer out, somebody got here first");
        }
      }
    }
  }

  synchronized void addSynchronized(final int key, final String message) {
    log.info("putting object");
    bufferMap.put(key, message);
    if (bufferMap.size() >= MAX_SIZE) {
        log.info("buffering-out {} elements", bufferMap.size());
        testRepo.write(bufferMap.values());
        log.info("clearing {} elements", bufferMap.size());
        bufferMap.clear();
    }
  }

  TimerTask dispatch() {
    return new TimerTask() {
      @Override
      public void run() {
        synchronized (bufferMap.entrySet()) {
          log.info("Dispatching {} messages due to timeout.", bufferMap.size());
          testRepo.write(bufferMap.values());
          bufferMap.clear();
        }
      }
    };
  }
}
