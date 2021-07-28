package com.se.fileserver.v1.common.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * 입력값이 잘못된 경우 발생하는 예외.
 * 메시지에 이유를 포함하여 반환하기 바람니다.
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