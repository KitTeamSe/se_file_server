package com.se.fileserver.v1.account.presentation.presenter;

import com.se.fileserver.v1.common.presentation.response.Response;

public interface AccountPresenter {
  Response<String> signIn(String token);
}
