package com.course.ai.assistant.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import com.course.ai.assistant.constant.AuthorizationDummyConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthBearerFilter extends OncePerRequestFilter {

  private static final String BEARER_PREFIX = "Bearer";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.isBlank(tokenHeader) || !tokenHeader.startsWith(BEARER_PREFIX)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Bearer is missing");
      return;
    }

    // validate bearer token
    var token = tokenHeader.substring(BEARER_PREFIX.length()).trim();

    if (!AuthorizationDummyConstants.VALID_BEARERS.contains(token)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Bearer token");
      return;
    }

    filterChain.doFilter(request, response);
  }

}
