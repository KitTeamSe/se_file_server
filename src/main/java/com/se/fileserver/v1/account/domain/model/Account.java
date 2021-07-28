package com.se.fileserver.v1.account.domain.model;

import com.se.fileserver.v1.account.domain.service.AccountService;
import com.se.fileserver.v1.common.domain.model.BaseEntity;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account extends BaseEntity implements AccountService {
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

  @Column(nullable = false, length = 20)
  @Enumerated(EnumType.STRING)
  private InformationOpenAgree informationOpenAgree;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  @JoinColumn(name = "question_id", referencedColumnName = "questionId", nullable = false)
  private Question question;

  @Column(length = 100, nullable = false)
  @Size(min = 2, max = 100)
  private String answer;

  public Account(@Size(min = 4, max = 20)String idString, String password) {
    this.idString = idString;
    this.password = password;
  }

  @Override
  public boolean isMatch(PasswordEncoder passwordEncoder, String rawPassword) {
    return passwordEncoder.matches(rawPassword, this.password);
  }
}
