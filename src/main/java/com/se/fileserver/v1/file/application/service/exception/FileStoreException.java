package com.se.fileserver.v1.file.application.service.exception;

import com.se.fileserver.v1.common.domain.exception.SeException;
import org.springframework.http.HttpStatus;

/*
* 파일 저장 시, 서버에서 파일을 저장할 수 없는 상황일 때 발생하는 예외
* */
public class FileStoreException extends SeException {

  public FileStoreException(String message) {
    super(HttpStatus.INSUFFICIENT_STORAGE, message);
  }

  public FileStoreException(String message, Throwable cause) {
    super(HttpStatus.INSUFFICIENT_STORAGE, message, cause);
  }

  public FileStoreException(Throwable cause) {
    super(HttpStatus.INSUFFICIENT_STORAGE, cause);
  }
}
