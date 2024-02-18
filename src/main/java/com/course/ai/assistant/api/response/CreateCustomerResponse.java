package com.course.ai.assistant.api.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerResponse {

  @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
  private UUID customerUuid;

  @Schema(example = "12345678")
  private String memberNumber;

}
