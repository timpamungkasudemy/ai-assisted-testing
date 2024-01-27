package com.course.ai.assistant.api.advice;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.course.ai.assistant.api.response.ErrorMessageResponse;
import com.course.ai.assistant.constant.ErrorMessageConstants;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

  @ExceptionHandler(value = { NoSuchElementException.class })
  public ResponseEntity<ErrorMessageResponse> handleAnyNotFoundException(Exception ex) {
    var errorMessage = ErrorMessageResponse.builder()
        .code(ErrorMessageConstants.CODE_DATA_NOT_FOUND)
        .cause(ex.getMessage())
        .message(ErrorMessageConstants.MESSAGE_DATA_NOT_FOUND)
        .build();

    return ResponseEntity.badRequest().body(errorMessage);
  }

}
