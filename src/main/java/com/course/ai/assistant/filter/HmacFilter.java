package com.course.ai.assistant.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import com.course.ai.assistant.constant.AuthorizationDummyConstants;
import com.course.ai.assistant.util.HmacUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Order(1)
public class HmacFilter extends OncePerRequestFilter {

  private static final String ALPHAMART_HMAC_HEADER = "X-Alphamart-Hmac";
  private static final String HMAC_DELIMITER = "|||";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // ignore filter on GET requests
    if (StringUtils.equalsIgnoreCase(request.getMethod(), HttpMethod.GET.name())) {
      filterChain.doFilter(request, response);
      return;
    }

    // retrieve HMAC header
    var hmacHeader = request.getHeader(ALPHAMART_HMAC_HEADER);

    if (StringUtils.isBlank(hmacHeader)) {
      response.sendError(HttpServletResponse.SC_FORBIDDEN, "HMAC header is missing");
      return;
    }

    // validate HMAC header
    var hmacValue = request.getHeader(ALPHAMART_HMAC_HEADER);
    var uri = request.getRequestURI() + request.getQueryString();
    var encodedUri = URLEncoder.encode(uri, StandardCharsets.UTF_8);
    var requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
    var requestBodyWithoutWhitespace = requestBody.replaceAll("\\s", "");
    var hmacMessage = request.getMethod().toUpperCase() + HMAC_DELIMITER + encodedUri + HMAC_DELIMITER
        + requestBodyWithoutWhitespace;

    try {
      if (!HmacUtil.isHmacMatch(hmacMessage, AuthorizationDummyConstants.HMAC_SECRET, hmacValue)) {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid HMAC header");
        return;
      }
    } catch (Exception ex) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
      return;
    }

    filterChain.doFilter(request, response);
  }
}
