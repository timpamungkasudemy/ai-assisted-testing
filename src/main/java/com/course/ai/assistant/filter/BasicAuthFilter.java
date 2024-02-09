package com.course.ai.assistant.filter;

import java.io.IOException;
import java.util.Base64;

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
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    filterChain.doFilter(request, response);
  }

}
