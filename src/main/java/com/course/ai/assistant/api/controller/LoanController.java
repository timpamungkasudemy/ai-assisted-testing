package com.course.ai.assistant.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.request.LoanCalculatorRequest;
import com.course.ai.assistant.api.response.LoanCalculatorResponse;
import com.course.ai.assistant.util.LoanUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/loan", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Loan")
public class LoanController {

  @PostMapping(path = "/calculator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Calculate monthly installment amount for a loan")
  public LoanCalculatorResponse postMethodName(@RequestBody @Valid LoanCalculatorRequest request) {
    var monthlyInstallmentAmount = LoanUtil.calculateMonthlyInstallment(request.getLoanAmount(),
        request.getTenureInMonths(),
        request.getAnnualInterestRate());

    return LoanCalculatorResponse.builder().monthlyInstallmentAmount(monthlyInstallmentAmount).build();
  }

}
