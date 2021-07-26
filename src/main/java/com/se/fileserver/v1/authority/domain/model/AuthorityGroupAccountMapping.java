package com.se.fileserver.v1.authority.domain.model;

import com.se.fileserver.v1.account.domain.model.Account;
import com.se.fileserver.v1.common.domain.model.AccountGenerateEntity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AuthorityGroupAccountMapping extends AccountGenerateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long authorityGroupAccountMappingId;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
  @JoinColumn(name = "account_id", referencedColumnName = "accountId", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
  @JoinColumn(name = "authority_group_id", referencedColumnName = "authorityGroupId", nullable = false)
  private AuthorityGroup authorityGroup;

  public AuthorityGroupAccountMapping(Account account, AuthorityGroup authorityGroup) {
    this.account = account;
    this.authorityGroup = authorityGroup;
  }
}

