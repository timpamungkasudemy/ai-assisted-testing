package com.course.ai.assistant.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.course.ai.assistant.filter.AuthBearerFilter;
import com.course.ai.assistant.filter.BasicAuthFilter;
import com.course.ai.assistant.filter.HmacFilter;

@Configuration
public class FilterConfig {

  @Bean
  FilterRegistrationBean<AuthBearerFilter> authBearerFilter() {
    var registrationBean = new FilterRegistrationBean<AuthBearerFilter>();

    registrationBean.setFilter(new AuthBearerFilter());
    registrationBean.addUrlPatterns("/api/product/*");

    return registrationBean;
  }

  @Bean
  FilterRegistrationBean<HmacFilter> hmacFilter() {
    var registrationBean = new FilterRegistrationBean<HmacFilter>();

    registrationBean.setFilter(new HmacFilter());
    registrationBean.addUrlPatterns("/api/transaction/*");

    return registrationBean;
  }

  @Bean
  FilterRegistrationBean<BasicAuthFilter> basicAuthFilter() {
    var registrationBean = new FilterRegistrationBean<BasicAuthFilter>();

    registrationBean.setFilter(new BasicAuthFilter());
    registrationBean.addUrlPatterns("/api/auth/login");

    return registrationBean;
  }

}
