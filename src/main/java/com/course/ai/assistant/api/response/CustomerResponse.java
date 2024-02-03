package com.course.ai.assistant.api.response;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.course.ai.assistant.constant.JsonFormatConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Address {
    @Schema(example = "Jl. Raya Bogor")
    private String street;
    @Schema(example = "Bogor")
    private String city;
    @Schema(example = "Jawa Barat")
    private String province;
    @Schema(example = "Indonesia")
    private String country;
    @Schema(example = "16111")
    private String zipCode;
    @Schema(example = "HOME")
    private AddressType type;
    private Coordinate coordinate;
  }

  public enum AddressType {
    HOME, OFFICE
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Contact {
    @Schema(example = "EMAIL")
    private ContactType type;
    @Schema(example = "someone@gmail.com")
    private String contactDetail;
  }

  public enum ContactType {
    EMAIL, PHONE_NUMBER, INSTAGRAM, FACEBOOK, TWITTER, LINKEDIN, WEBSITE
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Coordinate {
    @Schema(example = "-6.597147")
    private Double latitude;
    @Schema(example = "106.806039")
    private Double longitude;
  }

  @Schema(example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
  private UUID customerUuid;

  @Schema(example = "John Doe")
  private String fullName;

  @Schema(example = "2001-12-25")
  @JsonFormat(pattern = JsonFormatConstants.ISO_DATE_FORMAT)
  private LocalDate birthDate;

  @Schema(example = "12345678")
  private String memberNumber;

  private List<Address> addresses;

  private List<Contact> contacts;
}
