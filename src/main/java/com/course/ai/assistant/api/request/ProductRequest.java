package com.course.ai.assistant.api.request;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(value = com.fasterxml.jackson.databind.PropertyNamingStrategies.KebabCaseStrategy.class)
public class ProductRequest {

  @Max(value = 300, message = "Name must be less than or equal to 300 characters.")
  @Schema(description = "Product name", example = "Chocolate", required = true)
  private String name;

  @Max(value = 300, message = "Manufacturer must be less than or equal to 300 characters.")
  @Schema(description = "Product manufacturer", example = "Nestle", required = false)
  private String manufacturer;

  @Min(value = 0, message = "Price must be greater than or equal to 0.")
  @Schema(description = "Product price", example = "1.99", required = true)
  private double price;

  @Schema(description = "Product description", example = "Chocolate bar", required = false)
  private String description;

  @Pattern(regexp = "^[A-Z0-9]{8}$")
  @Schema(description = """
        <p>Stock Keeping Unit (SKU).</p>
        <p>Stock Keeping Unit must be 8 characters long and only contain capital letters and numbers.
        <strong>Stock Keeping Unit must be unique.</strong></p>
      """, example = "SKU12318", required = true)
  private String stockKeepingUnit;

  @Schema(description = "Product active status (default is <code>false</code>)", example = "true", required = false)
  private boolean active;

}
