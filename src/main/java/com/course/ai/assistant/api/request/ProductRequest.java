package com.course.ai.assistant.api.request;

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
public class ProductRequest {

  @Max(value = 300, message = "Name must be less than or equal to 300 characters.")
  private String name;

  @Max(value = 300, message = "Manufacturer must be less than or equal to 300 characters.")
  private String manufacturer;

  @Min(value = 0, message = "Price must be greater than or equal to 0.")
  private double price;

  private String description;

  @Pattern(regexp = "^[A-Z0-9]{8}$", message = """
            Stock Keeping Unit must be 8 characters long and only contain capital letters and numbers.
            Stock Keeping Unit must be unique.
      """)
  private String stockKeepingUnit;

  private boolean active;

}
