package com.course.ai.assistant.api.controller;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.util.DelayUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BasicController {

  @GetMapping(path = "/time", produces = MediaType.TEXT_PLAIN_VALUE)
  @Operation(summary = "Get current time")
  public String getTime() {
    return LocalTime.now().toString();
  }

  @GetMapping(path = "/fast", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fast response")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK") })
  public SimpleMessage fast() {
    return new SimpleMessage("Fast response from API");
  }

  @GetMapping(path = "/fast-random", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Fast response, produce random HTTP status.")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "415", description = "Unsupported media type"),
      @ApiResponse(responseCode = "500", description = "Internal server error") })
  public ResponseEntity<SimpleMessage> fastWithRandomStatus() {
    return responseWithRandomStatus();
  }

  private ResponseEntity<SimpleMessage> responseWithRandomStatus() {
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
      return ResponseEntity.ok(new SimpleMessage("Response from API"));
    }
  }

  @GetMapping(path = "/slow", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Slow response, takes 1-3 seconds")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK")

  })
  public SimpleMessage slow() {
    DelayUtil.delay(1000, 3000);
    return new SimpleMessage("Slow response from API");
  }

  @GetMapping(path = "/slow-random", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Slow response, takes 1-3 seconds. Produce random HTTP status.")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad request"),
      @ApiResponse(responseCode = "403", description = "Forbidden"),
      @ApiResponse(responseCode = "415", description = "Unsupported media type"),
      @ApiResponse(responseCode = "500", description = "Internal server error") })
  public ResponseEntity<SimpleMessage> slowWithRandomStatus() {
    DelayUtil.delay(1000, 3000);
    return responseWithRandomStatus();
  }

  @GetMapping(path = "/very-slow/{param}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Very slow response, takes 5 seconds")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK")

  })
  public SimpleMessage verySlow(@PathVariable("param") String param) {
    DelayUtil.delay(5000);
    return new SimpleMessage("Very slow response. The parameter is : " + param + ". Generated on "
        + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + ". Random number : "
        + RandomStringUtils.randomNumeric(4));
  }

  @GetMapping(path = "/who-am-i", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Get current IP address")
  @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK") })
  public SimpleMessage whoAmI() throws SocketException, UnknownHostException {
    return new SimpleMessage("I run on " + InetAddress.getLocalHost().getHostAddress());
  }

}
