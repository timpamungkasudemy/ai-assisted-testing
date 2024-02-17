package com.course.ai.assistant.api.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.response.TransactionResponse;
import com.github.javafaker.Faker;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Transaction")
@Slf4j
public class TransactionController {

  private static final List<String> PAYMENT_METHODS = List.of("CASH", "CREDIT_CARD", "DEBIT_CARD", "PAYPAL");

  @Autowired
  private Faker faker;

  @GetMapping(path = "/v1/transactions/fake", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get fake transactions by transaction IDs (UUID)", security = {
      @SecurityRequirement(name = "bearerAuth")
  })
  public List<TransactionResponse> getTransactions(
      @RequestParam(name = "transaction-ids", required = true) Set<String> transactionIds) {
    var response = new ArrayList<TransactionResponse>(transactionIds.size());

    for (var transactionId : transactionIds) {
      if (StringUtils.equals(transactionId, "00000000-0000-0000-0000-000000000000")) {
        continue;
      }

      try {
        var netAmount = Math.round(ThreadLocalRandom.current().nextDouble(5, 200) * 100.0) / 100.0;
        var taxAmount = Math.floor(netAmount * 0.1 * 100) / 100;
        var transactionDate = LocalDate.now().minusDays(ThreadLocalRandom.current().nextInt(0, 180));
        var transactionNumber = StringUtils.join("TRX-", transactionDate.getYear(), "-",
            RandomStringUtils.randomAlphanumeric(12).toUpperCase());

        var transaction = TransactionResponse.builder().currency("USD")
            .description(faker.food().vegetable() + ", " + faker.food().fruit())
            .paymentMethod(PAYMENT_METHODS.get(ThreadLocalRandom.current().nextInt(0, PAYMENT_METHODS.size())))
            .transactionDate(transactionDate)
            .netAmount(netAmount)
            .taxAmount(taxAmount)
            .transactionNumber(transactionNumber)
            .transactionId(UUID.fromString(transactionId))
            .build();

        response.add(transaction);
      } catch (IllegalArgumentException e) {
        log.warn(transactionId + " is not a valid UUID");
      }
    }

    response.sort((t1, t2) -> {
      var comparison = t2.getTransactionDate().compareTo(t1.getTransactionDate());

      if (comparison == 0) {
        return Double.compare(t2.getNetAmount(), t1.getNetAmount());
      }

      return comparison;
    });

    return response;
  }

}
