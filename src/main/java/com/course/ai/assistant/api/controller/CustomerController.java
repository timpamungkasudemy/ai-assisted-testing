package com.course.ai.assistant.api.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.response.Customer;

@RestController
@RequestMapping(path = "/api")
public class CustomerController {

  @GetMapping(path = "/v1/consumer", produces = MediaType.APPLICATION_JSON_VALUE)
  public Customer getCustomer(@RequestParam(required = true, name = "memberNumber") String memberNumber) {
    return Customer.builder().birthDate(LocalDate.of(1995, 4, 21)).customerUuid(UUID.randomUUID())
        .email("peter.parker@marvel.com").fullName("Peter Parker").memberNumber("M4890275210")
        .phoneNumber("628562190275").build();
  }

}
