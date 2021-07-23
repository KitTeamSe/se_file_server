package com.se.fileserver.v1.Account.application.dto;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

public class AccountFindIdByEmailDto {

  @Data
  @AllArgsConstructor
  static public class Response{
    @Size(min = 4, max = 20)
    String id;
  }

}
