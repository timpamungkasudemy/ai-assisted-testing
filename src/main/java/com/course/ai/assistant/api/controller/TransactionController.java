package com.course.ai.assistant.api.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.response.Transaction;
import com.course.ai.assistant.constant.JsonFormatConstants;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Transaction")
public class TransactionController {

  @GetMapping(path = "/v1/transactions/{customerUUID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Transaction> getTransactions(@PathVariable(required = true, name = "customerUUID") UUID customerUUID,
      @RequestParam(required = true, name = "fromDate") @DateTimeFormat(pattern = JsonFormatConstants.ISO_DATE_FORMAT) LocalDate fromDate,
      @RequestParam(required = true, name = "toDate") @DateTimeFormat(pattern = JsonFormatConstants.ISO_DATE_FORMAT) LocalDate toDate) {
    var transactionOne = Transaction.builder().currency("USD").description("www.alphamart.com").netAmount(28)
        .paymentMethod("CASH").transactionDate(LocalDate.of(2025, 2, 18)).transactionNumber("TRX-WEB-2025-000000014261")
        .build();
    var transactionTwo = Transaction.builder().currency("USD").description("android-2.4.6").netAmount(37)
        .paymentMethod("CREDITCARD").transactionDate(LocalDate.of(2025, 2, 20))
        .transactionNumber("TRX-AND-2025-000000021509").build();

    return List.of(transactionOne, transactionTwo);
  }

}
