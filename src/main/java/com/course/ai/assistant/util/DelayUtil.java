package com.course.ai.assistant.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelayUtil {

  private static final Logger log = LoggerFactory.getLogger(DelayUtil.class);

  public static void delay(int minMillis, int maxMillis) {
    try {
      Thread.sleep(
          ThreadLocalRandom.current().nextLong(minMillis, (maxMillis > minMillis ? maxMillis + 1 : minMillis + 1000)));
    } catch (InterruptedException e) {
      log.info("Interrupted " + e.getMessage());
    }
  }

  public static void delay(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep(millis);
    } catch (InterruptedException e) {
      log.info("Interrupted " + e.getMessage());
    }
  }

}
