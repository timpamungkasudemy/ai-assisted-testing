package com.course.ai.assistant.api.controller;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.response.SimpleMessageResponse;
import com.course.ai.assistant.util.DelayUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/basic", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Basic")
public class BasicController {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

  @RequestMapping(value = { "/echo" }, consumes = { MediaType.APPLICATION_JSON_VALUE,
      MediaType.TEXT_PLAIN_VALUE }, produces = MediaType.TEXT_PLAIN_VALUE)
  @Operation(summary = "Echo the HTTP request (URL, query parameters, headers, request body).")
  public ResponseEntity<String> echo(HttpServletRequest request,
      @RequestBody(required = false) @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request body", required = false) String body) {

    var response = new StringBuilder();

    // Append URL
    response.append("URL: ");
    response.append(request.getRequestURL());
    response.append(StringUtils.LF);
    response.append(StringUtils.LF);

    // Append HTTP request method
    response.append("HTTP Method: ");
    response.append(request.getMethod());
    response.append(StringUtils.LF);
    response.append(StringUtils.LF);

    // Append query parameters
    response.append("Query Parameters: ");
    response.append(StringUtils.LF);
    var parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      var paramName = parameterNames.nextElement();
      var paramValues = request.getParameterValues(paramName);
      response.append("  - ");
      response.append(paramName);
      response.append(": ");

      for (int i = 0; i < paramValues.length; i++) {
        response.append(paramValues[i]);
        if (i < paramValues.length - 1) {
          response.append(",");
        }
      }
      response.append(StringUtils.LF);
    }
    response.append(StringUtils.LF);

    // Append headers
    response.append("Headers: ");
    response.append(StringUtils.LF);
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      var headerName = headerNames.nextElement();
      var headerValues = request.getHeaders(headerName);
      response.append("  - ");
      response.append(headerName);
      response.append(": ");

      while (headerValues.hasMoreElements()) {
        var headerValue = headerValues.nextElement();
        response.append(headerValue);
        if (headerValues.hasMoreElements()) {
          response.append(", ");
        }
      }
      response.append(StringUtils.LF);
    }
    response.append(StringUtils.LF);

    // Append request body
    response.append("Request Body: ");
    response.append(StringUtils.LF);
    response.append(body);

    // Add custom response header "My-Custom-Header" with value is random 6 digits
    // uppercase alphanumeric
    var randomString = RandomStringUtils.randomAlphanumeric(3).toUpperCase() + "-"
        + RandomStringUtils.randomAlphanumeric(3).toUpperCase();
    var httpResponseHeaders = new org.springframework.http.HttpHeaders();
    httpResponseHeaders.add("My-Custom-Header", randomString);

    return ResponseEntity.ok().headers(httpResponseHeaders).contentType(MediaType.TEXT_PLAIN).body(response.toString());
  }

  @GetMapping(path = "/fast", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fast response")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK") })
  public SimpleMessageResponse fast() {
    return new SimpleMessageResponse("Response from API, random number: " + RandomStringUtils.randomNumeric(6));
  }

  @GetMapping(path = "/fast-random", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fast response, produce random HTTP status.")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "415", description = "Unsupported media type"),
      @ApiResponse(responseCode = "500", description = "Internal server error") })
  public ResponseEntity<SimpleMessageResponse> fastWithRandomStatus() {
    return responseWithRandomStatus();
  }

  @GetMapping(path = "/time", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get current time")
  public SimpleMessageResponse getTime() {
    var now = DATE_TIME_FORMATTER.format(LocalTime.now());
    return new SimpleMessageResponse(now);
  }

  private ResponseEntity<SimpleMessageResponse> responseWithRandomStatus() {
    var random = ThreadLocalRandom.current().nextInt(100);

    switch (random % 10) {
      case 0:
        return ResponseEntity.badRequest().body(null);
      case 1:
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
      case 2:
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(null);
      case 3:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
      default:
        return ResponseEntity
            .ok(new SimpleMessageResponse("Response from API, random number: " + RandomStringUtils.randomNumeric(6)));
    }
  }

  @GetMapping(path = "/slow", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Slow response, takes 1-3 seconds")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK")

  })
  public SimpleMessageResponse slow() {
    DelayUtil.delay(1000, 3000);
    return new SimpleMessageResponse("Slow response from API");
  }

  @GetMapping(path = "/slow-random", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Slow response, takes 1-3 seconds. Produce random HTTP status.")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "415", description = "Unsupported media type"),
      @ApiResponse(responseCode = "500", description = "Internal server error") })
  public ResponseEntity<SimpleMessageResponse> slowWithRandomStatus() {
    DelayUtil.delay(1000, 3000);
    return responseWithRandomStatus();
  }

  @GetMapping(path = "/very-slow/{param}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Very slow response, takes 5 seconds")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK")

  })
  public SimpleMessageResponse verySlow(@PathVariable("param") String param) {
    DelayUtil.delay(5000);
    return new SimpleMessageResponse("Very slow response. The parameter is : " + param + ". Generated on "
        + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ". Random number : "
        + RandomStringUtils.randomNumeric(6));
  }

  @GetMapping(path = "/who-am-i", produces = MediaType.TEXT_PLAIN_VALUE)
  @Operation(summary = "Get current IP address")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK") })
  public String whoAmI() throws SocketException, UnknownHostException {
    return "I run on " + InetAddress.getLocalHost().getHostAddress();
  }

  @GetMapping(path = "/slow-if-error", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = """
      Fast if the HTTP status response code is 2xx or 3xx. Otherwise, takes 1-3 seconds. Produce random HTTP status.
      """)
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "415", description = "Unsupported media type"),
      @ApiResponse(responseCode = "500", description = "Internal server error") })
  public ResponseEntity<SimpleMessageResponse> slowIfError() {
    var response = responseWithRandomStatus();

    if (!response.getStatusCode().is2xxSuccessful()) {
      DelayUtil.delay(1000, 3000);
    }

    return response;
  }
}
