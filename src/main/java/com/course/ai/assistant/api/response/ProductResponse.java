
package com.course.ai.assistant.api.response;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

  private UUID productUuid;
  private String name;
  private String manufacturer;
  private double basePrice;
  private String description;
  private String stockKeepingUnit;
  private boolean active;
  private ZonedDateTime createdAt;
  private ZonedDateTime updatedAt;
  private String createdBy;
  private String updatedBy;

}
