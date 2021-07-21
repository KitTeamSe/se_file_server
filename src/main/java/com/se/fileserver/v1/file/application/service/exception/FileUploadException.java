package com.se.fileserver.v1.file.application.service.exception;

public class FileUploadException extends RuntimeException{

  public FileUploadException(String message) {
    super(message);
  }

  public FileUploadException(String message, Throwable cause) {
    super(message, cause);
  }

}
