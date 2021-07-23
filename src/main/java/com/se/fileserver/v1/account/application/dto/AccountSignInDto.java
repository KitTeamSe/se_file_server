package com.se.fileserver.v1.account.application.dto;

public class AccountSignInDto {
  private String idString;
  private String password;

  public AccountSignInDto() {
  }

  public AccountSignInDto(String idString, String password) {
    this.idString = idString;
    this.password = password;
  }

  public String getIdString() {
    return idString;
  }

  public String getPassword() {
    return password;
  }
}
