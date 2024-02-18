package com.course.ai.assistant.api.controller;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.request.CreateCustomerRequest;
import com.course.ai.assistant.api.response.CreateCustomerResponse;
import com.course.ai.assistant.api.response.CustomerResponse;
import com.github.javafaker.Faker;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Customer")
public class CustomerController {

  @Autowired
  private Faker faker;

  @Operation(summary = "Get fake customer")
  @GetMapping(path = "/fake", produces = MediaType.APPLICATION_JSON_VALUE)
  public CustomerResponse getFakeCustomer() {
    return generateFakeCustomer();
  }

  // find customer by name
  @Operation(summary = "Search customer by name (case insensitive)")
  @GetMapping(path = "/fake/search", produces = MediaType.APPLICATION_JSON_VALUE)
  private List<CustomerResponse> findCustomerByName(
      @RequestParam(name = "name", required = true) @Parameter(description = "Customer name to search for", required = true, example = "john") String name) {
    final var customers = new ArrayList<CustomerResponse>();

    for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 5); i++) {
      final var customer = generateFakeCustomer();
      final var generateCorrectData = ThreadLocalRandom.current().nextInt(1, 5) < 4 ? true : false;

      if (generateCorrectData) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        customer.setFullName(name + " " + faker.name().lastName());
      } else {
        customer.setFullName(faker.name().fullName());
      }

      customers.add(customer);
    }

    return customers;
  }

  private CustomerResponse generateFakeCustomer() {
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
        .memberNumber(faker.number().digits(12))
        .build();
  }

  @PostMapping(path = "/fake", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Create fake customer", description = "<p>Create fake customer with given data. "
      + "If all submitted data are valid, will return random customer UUID and member number</p>")
  public ResponseEntity<CreateCustomerResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
    final var customerUuid = UUID.randomUUID();
    final var memberNumber = faker.number().digits(12);

    return ResponseEntity.created(null)
        .body(CreateCustomerResponse.builder().customerUuid(customerUuid).memberNumber(memberNumber).build());
  }
}
