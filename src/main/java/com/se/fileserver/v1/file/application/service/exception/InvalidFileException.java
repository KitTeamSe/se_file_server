package com.se.fileserver.v1.file.application.service.exception;

import com.se.fileserver.v1.common.domain.exception.SeException;
import org.springframework.http.HttpStatus;

public class InvalidFileException extends SeException {

  public InvalidFileException(String message) {
    super(HttpStatus.BAD_REQUEST, message);
  }

  public InvalidFileException(String message, Throwable cause) {
    super(HttpStatus.BAD_REQUEST, message, cause);
  }

  public InvalidFileException(Throwable cause) {
    super(HttpStatus.BAD_REQUEST, cause);
  }
}
