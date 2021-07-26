package com.se.fileserver.v1.common.application.dto;

public class Response<E> {
  private final int status;
  private String code;
  private final String message;
  private E data;

  public Response(int status, String message) {
    this.status = status;
    this.message = message;
    this.code = "SR01";
  }

  public Response(int status, String message, E data) {
    this(status, message);
    this.data = data;
  }

  public Response(int status, String message, String code) {
    this(status, message);
    this.code = code;
  }

  public Response(int status, String message, String code, E data) {
    this(status, message, data);
    this.code = code;
  }

  public int getStatus() {
    return status;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public E getData() {
    return data;
  }
}
