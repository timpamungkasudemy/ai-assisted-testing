package com.course.ai.assistant.api.response;

import java.time.LocalDate;
import java.util.UUID;

import com.course.ai.assistant.constant.JsonFormatConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

  private UUID transactionId;
  private String transactionNumber;

  @JsonFormat(pattern = JsonFormatConstants.ISO_DATE_FORMAT)
  private LocalDate transactionDate;

  private double netAmount;
  private double taxAmount;
  private double discountAmount;
  private String description;
  private String currency;
  private String paymentMethod;

}
