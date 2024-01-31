package com.course.ai.assistant.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationResponse {

  private int page;
  private int size;
  private long totalElements;
  private int totalPages;

}
