package com.se.fileserver.v1.common.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SuccessResponse<?> that = (SuccessResponse<?>) o;
    return code == that.code && Objects.equals(message, that.message) && Objects
        .equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, data);
  }

  @Override
  public String toString() {
    return "SuccessResponse{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", data=" + data +
        '}';
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public E getData() {
    return data;
  }

  public void setData(E data) {
    this.data = data;
  }
}
