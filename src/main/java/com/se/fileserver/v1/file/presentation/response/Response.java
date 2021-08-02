package com.se.fileserver.v1.file.presentation.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Response<E> {
  private HttpStatus status;
  private final String message;
  private E data;

  public Response(HttpStatus status, String message, E data) {
    this(status, message);
    this.data = data;
  }

  public Response(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
