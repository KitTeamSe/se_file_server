package com.se.fileserver.v1.common.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * 토큰이 만료되거나 유효하지 않은 경우 발생하는 예외.
 */
public class InvalidTokenException extends SeException {

  public InvalidTokenException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }

  public InvalidTokenException(String message, Throwable cause) {
    super(HttpStatus.UNAUTHORIZED, message, cause);
  }

  public InvalidTokenException(Throwable cause) {
    super(HttpStatus.UNAUTHORIZED, cause);
  }
}