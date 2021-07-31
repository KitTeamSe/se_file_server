package com.se.fileserver.v1.authority.domain.model;

import com.se.fileserver.v1.common.domain.model.BaseEntity;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AuthorityGroupAuthorityMapping extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long authorityGroupAuthorityMappingId;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
  @JoinColumn(name = "authority_id", referencedColumnName = "authorityId")
  private Authority authority;

  @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
  @JoinColumn(name = "authority_group_id", referencedColumnName = "authorityGroupId")
  private AuthorityGroup authorityGroup;

  public AuthorityGroupAuthorityMapping(Authority authority, AuthorityGroup authorityGroup) {
    this.authority = authority;
    this.authorityGroup = authorityGroup;
  }

  public void setAuthority(Authority authority) {
    this.authority = authority;
    if(authority == null)
      return;
    if(!authority.getAuthorityGroupAuthorityMapping().contains(this))
      authority.addAuthorityGroupAuthorityMapping(this);
  }
}
