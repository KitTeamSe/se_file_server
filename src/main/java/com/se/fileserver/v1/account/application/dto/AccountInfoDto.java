package com.se.fileserver.v1.account.application.dto;

import com.se.fileserver.v1.account.domain.model.AccountType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountInfoDto {
  @ApiModelProperty(example = "account", notes = "아이디")
  @Size(min = 4, max = 20)
  private String idString;

  @ApiModelProperty(example = "password", notes = "비밀번호")
  @Size(min = 8, max = 20)
  private String password;

  @ApiModelProperty(example = "name", notes = "이름")
  @Size(min = 2, max = 20)
  private String name;

  @ApiModelProperty(example = "nickname", notes = "닉네임")
  @Size(min = 2, max = 20)
  private String nickname;

  @ApiModelProperty(example = "11110000", notes = "학번")
  @Size(min = 8, max = 20)
  private String studentId;

  @ApiModelProperty(example = "STUDENT", notes = "사용자 타입")
  private AccountType type;

  @ApiModelProperty(example = "01012345678", notes = "전화번호, 00011112222 형식")
  @Size(min = 10, max = 20)
  private String phoneNumber;

  @ApiModelProperty(example = "abc@def.com", notes = "이메일")
  @Email
  private String email;

  @Builder
  public AccountInfoDto(@Size(min = 4, max = 20)String idString, @Size(min = 8, max = 20)String password, @Size(min = 2, max = 20)String name,
      @Size(min = 2, max = 20)String nickname, @Size(min = 8, max = 20)String studentId, AccountType type, @Size(min = 10, max = 20)String phoneNumber, String email) {
    this.idString = idString;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.studentId = studentId;
    this.type = type;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }
}
