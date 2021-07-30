package com.se.fileserver.v1.account.presentation.presenter;

import com.se.fileserver.v1.common.presentation.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountPresenterFormatter implements AccountPresenter{

  @Override
  public Response<String> signIn(String token) {
    return new Response<>(HttpStatus.OK, "성공적으로 로그인 되었습니다.", token);
  }
}
