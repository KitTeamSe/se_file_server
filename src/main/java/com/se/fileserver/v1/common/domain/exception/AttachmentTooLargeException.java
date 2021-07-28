package com.se.fileserver.v1.common.domain.exception;

import org.springframework.http.HttpStatus;

/**
 * 사용자가 제한 크기를 초과한 파일을 첨부하려 하는 경우의 예외.
 */
public class AttachmentTooLargeException extends SeException {

  public AttachmentTooLargeException(String message) {
    super(HttpStatus.PAYLOAD_TOO_LARGE, message);
  }

  public AttachmentTooLargeException(String message, Throwable cause) {
    super(HttpStatus.PAYLOAD_TOO_LARGE, message, cause);
  }

  public AttachmentTooLargeException(Throwable cause) {
    super(HttpStatus.PAYLOAD_TOO_LARGE, cause);
  }
}