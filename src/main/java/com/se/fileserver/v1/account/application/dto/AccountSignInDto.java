package com.se.fileserver.v1.account.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSignInDto {
  private String id;
  private String password;

  public AccountSignInDto(String id, String password) {
    this.id = id;
    this.password = password;
  }
}
