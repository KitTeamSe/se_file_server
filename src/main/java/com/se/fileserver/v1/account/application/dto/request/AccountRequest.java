package com.se.fileserver.v1.account.application.dto.request;

import com.se.fileserver.v1.account.application.dto.common.CommonRequest;

public class AccountRequest<T> extends CommonRequest<T> {
  /*
  additional field
  * */
  public AccountRequest() {}
  public AccountRequest(T dto) {
    super(dto);
  }
}
