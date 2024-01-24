package com.course.ai.assistant.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Course - Alphamart", version = "1.0.0", description = "API Documentation for Alphamart"))
public class SpringDocConfig {

  @Bean
  GroupedOpenApi publicApi() {
    return GroupedOpenApi.builder().group("Alphamart").pathsToMatch("/api/**").build();
  }

}
