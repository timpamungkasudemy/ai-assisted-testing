package com.course.ai.assistant.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import com.course.ai.assistant.constant.AuthorizationDummyConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BasicAuthFilter extends OncePerRequestFilter {

  private static final String BASIC_AUTHENTICATION_PREFIX = "Basic";

  private static final Map<String, Integer> RATE_LIMITS = new HashMap<>();

  private static final int MAX_GUEST_RATE_LIMIT = 30;

  public static void resetRateLimits() {
    RATE_LIMITS.clear();
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(BASIC_AUTHENTICATION_PREFIX)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Basic is missing");
      return;
    }

    var base64Credentials = authHeader.substring(BASIC_AUTHENTICATION_PREFIX.length()).trim();
    var credentials = new String(Base64.getDecoder().decode(base64Credentials));
    var values = credentials.split(":", 2);

    if (!AuthorizationDummyConstants.VALID_USERS.containsKey(values[0].toLowerCase())
        || !AuthorizationDummyConstants.VALID_USERS.get(values[0].toLowerCase()).equals(values[1])) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username or password");
      return;
    }

    // put rate limit for guest user
    var username = values[0].toLowerCase();

    if (StringUtils.equalsIgnoreCase(values[0].toLowerCase(), "guest")) {
      var rateLimit = RATE_LIMITS.getOrDefault(username, 0);

      if (rateLimit > MAX_GUEST_RATE_LIMIT) {
        response.sendError(429, "Rate limit exceeded for user " + username + ". Wait 1 minute and try again.");
        return;
      }

      RATE_LIMITS.put(username, rateLimit + 1);
    }

    filterChain.doFilter(request, response);
  }

}
