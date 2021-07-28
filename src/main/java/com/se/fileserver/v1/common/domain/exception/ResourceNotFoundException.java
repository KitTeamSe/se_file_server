package com.se.fileserver.v1.common.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * 클라이언트가 요청한 리소스가 존재하지 않을 때 발생하는 예외.
 */
public class ResourceNotFoundException extends SeException {

  public ResourceNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }

  public ResourceNotFoundException(String message,
      Throwable cause) {
    super(HttpStatus.NOT_FOUND, message, cause);
  }

  public ResourceNotFoundException(Throwable cause) {
    super(HttpStatus.NOT_FOUND, cause);
  }
}

