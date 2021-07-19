package com.se.fileserver.v1.common.domain.error;

import com.fasterxml.jackson.annotation.JsonFormat;

//TODO : 에러 컨벤션 확보 필요
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GlobalErrorCode implements ErrorCode {

  INVALID_INPUT_VALUE(400, "GE01", "올바르지 않은 입력"),
  INVALID_JSON_INPUT(400, "GE04", "올바르지 않은 JSON 입력"),
  BANNED_IP(400, "GE06", "차단된 사용자"),
  EXPIRED_JWT_TOKEN(401, "GE05", "JWT 토큰 기한 만료"),
  HANDLE_ACCESS_DENIED(403, "GE03", "권한 없음"),
  METHOD_NOT_ALLOWED(405, "GE02", "허용되지 않은 메소드"),
  UNKNOWN_NON_BUSINESS_ERROR(501, "GE07", "처리되지 않은 오류 발생");

  private final String code;
  private final String message;
  private int status;

  GlobalErrorCode(final int status, final String code, final String message) {
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