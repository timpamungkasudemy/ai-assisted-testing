package com.course.ai.assistant.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanCalculatorRequest {

  @Min(value = 1, message = "Loan amount must 1 or greater")
  @Max(value = 1000000, message = "Loan amount must 1000000 or less")
  @Schema(description = "Loan amount in USD", example = "5000", required = true)
  private double loanAmount;

  @Min(value = 1, message = "Tenure in months must be 1 or greater")
  @Max(value = 60, message = "Tenure in months must 60 or less")
  @Schema(description = "Tenure in months", example = "12", required = true)
  private int tenureInMonths;

  @Min(value = 0, message = "Annual interest rate must 0 or greater")
  @Max(value = 1, message = "Annual interest rate must be less than 1")
  @Schema(description = "Annual interest rate", example = "0.1", required = true)
  private double annualInterestRate;

}
