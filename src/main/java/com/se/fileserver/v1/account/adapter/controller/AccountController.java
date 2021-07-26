package com.se.fileserver.v1.account.adapter.controller;

import com.se.fileserver.v1.account.application.dto.AccountInfoDto;
import com.se.fileserver.v1.account.application.dto.AccountSignInDto;
import com.se.fileserver.v1.common.application.dto.Response;
import com.se.fileserver.v1.account.application.dto.request.AccountRequest;
import com.se.fileserver.v1.account.application.service.AccountSignInService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file-server/v1")
@Api(tags = "사용자 관리")
public class AccountController {
  private final AccountSignInService accountSignInService;  //

  @Autowired
  public AccountController(
      AccountSignInService accountSignInService) {
    this.accountSignInService = accountSignInService;
  }

  @PostMapping(value = "/account/signin")
  @ResponseStatus(code = HttpStatus.OK)
  @ApiOperation(value = "로그인")
  public Response<String> singIn(@RequestBody @Validated AccountRequest<AccountSignInDto> request,
      HttpServletRequest httpServletRequest){
    String token = accountSignInService.signIn(request.getDto(), getIp(httpServletRequest));
    return new Response<>(HttpStatus.OK.value(), "성공적으로 로그인 되었습니다.", token);
  }

  private String getIp(HttpServletRequest httpServletRequest){
    String ip = httpServletRequest.getHeader("x-forwarded-for");
    return ip != null ? ip : httpServletRequest.getRemoteAddr();
  }
}
