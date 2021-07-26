package com.se.fileserver.v1.account.domain.model;

import com.se.fileserver.v1.common.domain.model.BaseEntity;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AccountVerifyToken extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long accountVerifyUrl;

  @Column(length = 40)
  @Email
  private String email;

  @Column(length = 255, unique = true)
  private String token;

  private LocalDateTime timeExpire;

  @Column(length = 30)
  @Enumerated(EnumType.STRING)
  private AccountVerifyStatus status;

  public AccountVerifyToken(@Email String email, String token, LocalDateTime timeExpire,
      AccountVerifyStatus status) {
    this.email = email;
    this.token = token;
    this.timeExpire = timeExpire;
    this.status = status;
  }

  public void verify() {
    this.status = AccountVerifyStatus.VERIFIED;
  }
}

