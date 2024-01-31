
package com.course.ai.assistant.api.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchResponse {

  private PaginationResponse pagination;
  private List<ProductResponse> data;

}
