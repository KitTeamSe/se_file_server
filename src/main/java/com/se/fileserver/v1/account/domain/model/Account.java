package com.se.fileserver.v1.account.domain.model;

import com.se.fileserver.v1.common.domain.model.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Account extends BaseEntity {
  public static final String MANAGE_TOKEN = "ACCOUNT_MANAGE";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Long accountId;

  @Size(min = 4, max = 20)
  @Column(nullable = false, unique = true)
  private String idString;

  @Column(nullable = false)
  private String password;

  @Size(min = 2, max = 20)
  @Column(nullable = false)
  private String name;

  @Size(min = 2, max = 20)
  @Column(nullable = false, unique = true)
  private String nickname;

  @Size(min = 8, max = 20)
  @Column(nullable = false, unique = true)
  private String studentId;

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private AccountType type;

  @Size(min = 10, max = 20)
  @Column(length = 20)
  private String phoneNumber;

  @Size(min = 4, max = 40)
  @Column(nullable = false, unique = true)
  @Email
  private String email;

  @Column(length = 20)
  @Size(min = 4, max = 20)
  private String lastSignInIp;

  public Account() {}

  public static class Builder{
    public static final String MANAGE_TOKEN = "ACCOUNT_MANAGE";
    private Long accountId;
    @Size(min = 4, max = 20)
    private String idString;
    private String password;
    @Size(min = 2, max = 20)
    private String name;
    @Size(min = 2, max = 20)
    private String nickname;
    @Size(min = 8, max = 20)
    private String studentId;
    private AccountType type;
    @Size(min = 10, max = 20)
    private String phoneNumber;
    @Size(min = 4, max = 40)
    private String email;
    @Size(min = 4, max = 20)
    private String lastSignInIp;

    public Builder(){}

    public Builder accountId(Long accountId) {
      this.accountId = accountId;
      return this;
    }

    public Builder idString(String idString) {
      this.idString = idString;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder nickname(String nickname) {
      this.nickname = nickname;
      return this;
    }

    public Builder studentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public Builder type(AccountType type) {
      this.type = type;
      return this;
    }

    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder email(String email) {
      this.email = email;
      return this;
    }

    public Builder lastSignInIp(String lastSignInIp) {
      this.lastSignInIp = lastSignInIp;
      return this;
    }

    public Account build(){
      return new Account(this);
    }
  }

  public Account(Builder builder) {
    this.accountId = builder.accountId;
    this.idString = builder.idString;
    this.password = builder.password;
    this.name = builder.name;
    this.nickname = builder.nickname;
    this.studentId = builder.studentId;
    this.type = builder.type;
    this.phoneNumber = builder.phoneNumber;
    this.email = builder.email;
    this.lastSignInIp = builder.lastSignInIp;
  }

  public Long getAccountId() {
    return accountId;
  }

  public String getIdString() {
    return idString;
  }

  public boolean isMatch(String plainPassword){
    return true;
  }
}
