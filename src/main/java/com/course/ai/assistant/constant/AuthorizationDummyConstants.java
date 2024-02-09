package com.course.ai.assistant.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

public final class AuthorizationDummyConstants {

  public static final List<String> VALID_BEARERS = new ArrayList<>(100);
  public static final Map<String, String> VALID_USERS = new HashMap<>();
  public static final String HMAC_SECRET = "ThisIsTheValidHmacSecret";

  static {
    for (int i = 0; i < 200; i++) {
      VALID_BEARERS.add(RandomStringUtils.randomAlphanumeric(64));
    }

    VALID_USERS.put("administrator".toLowerCase(), "AdministratorPassword");
    VALID_USERS.put("operator.one".toLowerCase(), "OperatorOnePassword");
    VALID_USERS.put("operator.two".toLowerCase(), "OperatorTwoPassword");
  }

}
