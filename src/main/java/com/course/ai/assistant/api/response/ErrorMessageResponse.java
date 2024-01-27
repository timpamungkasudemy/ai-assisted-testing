package com.course.ai.assistant.api.response;

import java.time.ZonedDateTime;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageResponse {

  @Nonnull
  private String code;

  private String cause;

  private String message;

  @Builder.Default
  private ZonedDateTime timestamp = ZonedDateTime.now();

}
