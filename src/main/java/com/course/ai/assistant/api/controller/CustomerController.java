package com.course.ai.assistant.api.controller;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.response.CustomerResponse;
import com.github.javafaker.Faker;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Customer")
public class CustomerController {

  private static final Faker faker = new Faker();

  @Operation(summary = "Get fake customer")
  @GetMapping(path = "/fake", produces = MediaType.APPLICATION_JSON_VALUE)
  public CustomerResponse getFakeCustomer() {
    final var birthDate = faker.date().birthday(18, 65).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    final var homeAddress = CustomerResponse.Address.builder().street(faker.address().streetAddress())
        .city(faker.address().city())
        .province(faker.address().state())
        .country(faker.address().country())
        .zipCode(faker.address().zipCode())
        .type(CustomerResponse.AddressType.HOME)
        .coordinate(CustomerResponse.Coordinate.builder().latitude(Double.parseDouble(faker.address().latitude()))
            .longitude(Double.parseDouble(faker.address().longitude()))
            .build())
        .build();
    final var officeAddress = CustomerResponse.Address.builder().street(faker.address().streetAddress())
        .city(faker.address().city())
        .province(faker.address().state())
        .country(faker.address().country())
        .zipCode(faker.address().zipCode())
        .type(CustomerResponse.AddressType.OFFICE)
        .coordinate(CustomerResponse.Coordinate.builder().latitude(Double.parseDouble(faker.address().latitude()))
            .longitude(Double.parseDouble(faker.address().longitude()))
            .build())
        .build();
    final var contactEmail = CustomerResponse.Contact.builder().type(CustomerResponse.ContactType.EMAIL)
        .contactDetail(faker.internet().emailAddress())
        .build();
    final var contactPhoneNumber = CustomerResponse.Contact.builder().type(CustomerResponse.ContactType.PHONE_NUMBER)
        .contactDetail(faker.phoneNumber().phoneNumber())
        .build();
    final var contactInstagram = CustomerResponse.Contact.builder().type(CustomerResponse.ContactType.INSTAGRAM)
        .contactDetail(faker.name().username())
        .build();

    final var addresses = new ArrayList<CustomerResponse.Address>();

    final var contacts = new ArrayList<CustomerResponse.Contact>();
    contacts.add(contactEmail);

    // randomly add homeAddress, officeAddress, or both to addresses
    switch (ThreadLocalRandom.current().nextInt(3)) {
      case 0:
        addresses.add(homeAddress);
        break;
      case 1:
        addresses.add(officeAddress);
        break;
      case 2:
        addresses.add(homeAddress);
        addresses.add(officeAddress);
        break;
      default:
        break;
    }

    // randomly add either contactPhoneNumber, contactInstagram, or both to contacts
    switch (ThreadLocalRandom.current().nextInt(3)) {
      case 0:
        contacts.add(contactPhoneNumber);
        break;
      case 1:
        contacts.add(contactInstagram);
        break;
      case 2:
        contacts.add(contactPhoneNumber);
        contacts.add(contactInstagram);
        break;
      default:
        break;
    }

    Collections.shuffle(contacts);

    return CustomerResponse.builder().birthDate(birthDate)
        .addresses(addresses)
        .birthDate(birthDate)
        .contacts(contacts)
        .customerUuid(UUID.randomUUID())
        .fullName(faker.name().fullName())
        .memberNumber(faker.number().digits(8))
        .build();
  }
}
