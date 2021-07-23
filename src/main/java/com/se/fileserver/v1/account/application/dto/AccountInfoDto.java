package com.se.fileserver.v1.account.application.dto;

import com.se.fileserver.v1.account.domain.model.AccountType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class AccountInfoDto {
  @ApiModelProperty(example = "account", notes = "아이디")
  @Size(min = 4, max = 20)
  private String id;

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

  public AccountInfoDto(String id, String password, String name, String nickname,
      String studentId, AccountType type, String phoneNumber, String email) {
    this.id = id;
    this.password = password;
    this.name = name;
    this.nickname = nickname;
    this.studentId = studentId;
    this.type = type;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  public AccountInfoDto() {
  }

  public AccountInfoDto(Builder builder) {
  }

  public static class Builder {
    private String id;
    private String password;
    private String name;
    private String nickname;
    private String studentId;
    private AccountType type;
    private String phoneNumber;
    private String email;

    public Builder(){}

    public AccountInfoDto.Builder id(String id){
      this.id = id;
      return this;
    }
    public AccountInfoDto.Builder password(String password){
      this.password = password;
      return this;
    }
    public AccountInfoDto.Builder name(String name){
      this.name = name;
      return this;
    }
    public AccountInfoDto.Builder nickname(String nickname){
      this.nickname = nickname;
      return this;
    }
    public AccountInfoDto.Builder studentId(String studentId){
      this.studentId = studentId;
      return this;
    }
    public AccountInfoDto.Builder type(AccountType type){
      this.type = type;
      return this;
    }
    public AccountInfoDto.Builder phoneNumber(String phoneNumber){
      this.phoneNumber = phoneNumber;
      return this;
    }
    public AccountInfoDto.Builder email(String email){
      this.email = email;
      return this;
    }

    public AccountInfoDto build(){
      return new AccountInfoDto(this);
    }
  }

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public String getNickname() {
    return nickname;
  }

  public String getStudentId() {
    return studentId;
  }

  public AccountType getType() {
    return type;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getEmail() {
    return email;
  }
}
