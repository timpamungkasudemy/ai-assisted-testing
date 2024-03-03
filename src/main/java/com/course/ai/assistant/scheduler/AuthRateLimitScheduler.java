package com.course.ai.assistant.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.course.ai.assistant.filter.BasicAuthFilter;

@Component
public class AuthRateLimitScheduler {

  @Scheduled(fixedRate = 60000)
  public void resetRateLimits() {
    BasicAuthFilter.resetRateLimits();
  }

}
