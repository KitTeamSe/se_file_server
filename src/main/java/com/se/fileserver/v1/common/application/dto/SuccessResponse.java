package com.se.fileserver.v1.common.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.jfr.DataAmount;

public class SuccessResponse<E> {
  private int code;
  private String message;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private E data;

  public SuccessResponse(int code, String message, E data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public SuccessResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }

}
