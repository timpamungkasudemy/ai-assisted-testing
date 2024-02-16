package com.course.ai.assistant.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomDelayResponse {

  private int delay;
  private String identifier;
  private String message;

}
