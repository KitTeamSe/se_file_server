package com.se.fileserver.v1.common.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * 잘못된 요청을 하였을 때 발생하는 에외.
 * 서버가 요청을 이해할 수 없음을 의미합니다.
 */
public class BadRequestException extends SeException {

  public BadRequestException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(HttpStatus.BAD_REQUEST, message, cause);
  }

  public BadRequestException(Throwable cause) {
    super(HttpStatus.BAD_REQUEST, cause);
  }
}
