package com.se.fileserver.v1.common.domain.exception;

import com.se.fileserver.v1.common.domain.error.ErrorCode;

//TODO : 에러 컨벤션 확보 필요
public class BusinessException extends RuntimeException {

  private ErrorCode errorCode;

  public ErrorCode getErrorCode() {
    return errorCode;
  }

  public BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
