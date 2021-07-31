package com.se.fileserver.v1.account.application.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSignInDto {
  @Size(min = 4, max = 20)
  private String id;
  @NotBlank
  private String password;

  public AccountSignInDto(String id, String password) {
    this.id = id;
    this.password = password;
  }
}
