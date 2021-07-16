package com.se.fileserver.v1.common.domain.error;

public interface ErrorCode {

  String getCode();

  String getMessage();

  int getStatus();

}
