package com.course.ai.assistant.api.request;

import java.time.LocalDate;
import java.util.List;

import com.course.ai.assistant.constant.JsonFormatConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CreateCustomerRequest", description = "Request body to create a new customer")
public class CreateCustomerRequest {

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
    @Pattern(regexp = "^EMAIL|PHONE_NUMBER|INSTAGRAM|FACEBOOK|TWITTER|LINKEDIN|WEBSITE$", message = "Invalid contact type. Only EMAIL, PHONE_NUMBER, INSTAGRAM, FACEBOOK, TWITTER, LINKEDIN, WEBSITE are allowed.")
    private ContactType type;

    @Schema(example = "someone@gmail.com")
    @NotBlank(message = "Contact detail is required")
    @Size(min = 3, max = 100, message = "Contact detail must be between 3 and 100 characters")
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

  @Schema(example = "John Doe")
  @Pattern(regexp = "^[a-zA-Z ]{3,100}$", message = "Full name must match the pattern ^[a-zA-Z ]{3,100}$")
  @NotNull(message = "Full name is required")
  private String fullName;

  @Schema(example = "2001-12-25")
  @JsonFormat(pattern = JsonFormatConstants.ISO_DATE_FORMAT)
  @NotNull(message = "Birth date is required")
  @Past(message = "Birth date must be in the past")
  private LocalDate birthDate;

  private List<Address> addresses;

  @NotEmpty(message = "At least one contact is required")
  private List<Contact> contacts;
}
