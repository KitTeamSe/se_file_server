package com.se.fileserver.v1.common.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * 클라이언트가 요청한 리소스가 존재하지 않을 때 발생하는 예외.
 */
public class InvalidInputException extends SeException {

  public InvalidInputException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

  public InvalidInputException(String message, Throwable cause) {
    super(HttpStatus.BAD_REQUEST, message, cause);
  }

  public InvalidInputException(Throwable cause) {
    super(HttpStatus.BAD_REQUEST, cause);
  }
}