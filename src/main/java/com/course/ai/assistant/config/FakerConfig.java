package com.course.ai.assistant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.datafaker.Faker;

@Configuration
public class FakerConfig {

  @Bean
  Faker faker() {
    return new Faker();
  }

}
