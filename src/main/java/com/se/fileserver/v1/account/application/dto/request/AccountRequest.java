package com.se.fileserver.v1.account.application.dto.request;

import com.se.fileserver.v1.common.application.dto.request.BaseRequest;

public class AccountRequest<T> extends BaseRequest<T> {
  /*
  additional field
  * */
  public AccountRequest() {}
  public AccountRequest(T dto) {
    super(dto);
  }
}
