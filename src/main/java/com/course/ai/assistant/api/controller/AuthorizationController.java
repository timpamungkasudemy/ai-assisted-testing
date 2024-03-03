package com.course.ai.assistant.api.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.ai.assistant.api.response.AccessTokenResponse;
import com.course.ai.assistant.constant.AuthorizationDummyConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authorization")
public class AuthorizationController {

  @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Login to get access token", description = """
      <p>
      Valid username & passwords for sample are:
      <ul>
        <li><code>administrator / AdministratorPassword</code></li>
        <li><code>operator.one / OperatorOnePassword</code></li>
        <li><code>operator.two / OperatorTwoPassword</code></li>
      </ul>
      The usernames above does not have rate limits.
      </p>
      <p>
      A username <code>guest</code> is also available with password <code>GuestPassword</code>.
      The rate limit for <code>guest</code> user is <strong>30 requests per minute</strong>.
      </p>
      """, security = {
      @SecurityRequirement(name = "basicAuth")
  })
  public ResponseEntity<AccessTokenResponse> login(@RequestHeader("Authorization") String authorizationHeader) {
    var validToken = AuthorizationDummyConstants.VALID_BEARERS
        .get(ThreadLocalRandom.current().nextInt(0, AuthorizationDummyConstants.VALID_BEARERS.size()));
    return ResponseEntity.ok().body(AccessTokenResponse.builder().accessToken(validToken).build());
  }

}
