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
public class Customer {

  private UUID customerUuid;
  private String fullName;
  private String email;

  @JsonFormat(pattern = JsonFormatConstants.ISO_DATE_FORMAT)
  private LocalDate birthDate;

  private String phoneNumber;
  private String memberNumber;

}
