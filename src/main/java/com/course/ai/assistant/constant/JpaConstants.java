package com.course.ai.assistant.constant;

public final class JpaConstants {

  public static final String DEFAULT_CREATED_BY = "administrator";
  public static final String DEFAULT_UPDATED_BY = "administrator";

  public static enum ActiveQueryFlag {
    ACTIVE_ONLY, INACTIVE_ONLY, ALL
  }

  public static enum SortDirection {
    ASC, DESC
  }

}
