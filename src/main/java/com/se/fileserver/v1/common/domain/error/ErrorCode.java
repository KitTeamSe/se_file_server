package com.se.fileserver.v1.common.domain.error;

//TODO : 에러 컨벤션 확보 후 작성
public interface ErrorCode {

  String getCode();

  String getMessage();

  int getStatus();

}
