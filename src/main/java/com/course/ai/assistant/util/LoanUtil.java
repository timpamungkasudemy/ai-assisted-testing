package com.course.ai.assistant.util;

public class LoanUtil {

  public static double calculateMonthlyInstallment(double loanAmount, int tenureInMonths, double annualInterestRate) {
    double monthlyInterestRate = annualInterestRate / 12 / 100;
    double monthlyPayment = loanAmount * monthlyInterestRate / (1 - Math.pow(1 + monthlyInterestRate, -tenureInMonths));
    return Math.round(monthlyPayment * 100.0) / 100.0; // rounding to two decimal places
  }

}
