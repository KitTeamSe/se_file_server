package com.se.fileserver.v1.file.application.service.error;

import com.se.fileserver.v1.common.domain.error.ErrorCode;

public enum FileErrorCode implements ErrorCode {

  UPLOAD_PATH_DOES_NOT_EXISTS(400, "FUE01", "업로드할 경로가 존재하지 않음"),
  INVALID_FILE_NAME(400, "FUE02", "유효하지 않은 파일명"),
  INVALID_FILE_SIZE(400, "FUE03", "유효하지 않은 파일 크기"),
  DOWNLOAD_PATH_DOES_NOT_EXISTS(400, "FDE01", "파일을 다운로드 할 경로가 존재하지 않음"),
  FILE_DOES_NOT_EXISTS(400, "FDE02", "파일을 찾지 못함"),
  UNKNOWN_UPLOAD_ERROR_CAUSED(501, "FUE04", "업로드 도중 알 수 없는 오류 발생");

  private final String code;
  private final String message;
  private int status;

  FileErrorCode(final int status, final String code, final String message) {
    this.status = status;
    this.message = message;
    this.code = code;
  }
  @Override
  public String getCode() {
    return code;
  }
  @Override
  public String getMessage() {
    return message;
  }
  @Override
  public int getStatus() {
    return status;
  }

}
