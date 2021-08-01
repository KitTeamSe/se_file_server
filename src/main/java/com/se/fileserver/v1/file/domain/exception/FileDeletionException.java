package com.se.fileserver.v1.file.domain.exception;

import com.se.fileserver.v1.common.domain.exception.SeException;
import org.springframework.http.HttpStatus;

/**
  * 파일 삭제 실패 시 발생하는 예외
  */

public class FileDeletionException extends SeException {

  public FileDeletionException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }

  public FileDeletionException(String message, Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message, cause);
  }

  public FileDeletionException(Throwable cause) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, cause);
  }
}
