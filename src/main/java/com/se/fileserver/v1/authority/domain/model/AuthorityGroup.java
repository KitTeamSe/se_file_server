package com.se.fileserver.v1.authority.domain.model;

import com.se.fileserver.v1.common.domain.model.AccountGenerateEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AuthorityGroup extends AccountGenerateEntity implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long authorityGroupId;

  @Column(length = 30, nullable = false, unique = true)
  @Size(min = 2, max = 30)
  private String name;

  @Column(length = 100, nullable = false)
  @Size(min = 2, max = 100)
  private String description;

  @Column(length = 30, nullable = false)
  @Enumerated(EnumType.STRING)
  private AuthorityGroupType type;

  public AuthorityGroup(@Size(min = 2, max = 30) String name, @Size(min = 2, max = 100) String description, AuthorityGroupType type) {
    this.name = name;
    this.description = description;
    this.type = type;
  }

  public void updateDescription(String description) {
    this.description = description;
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void remove() {
//    if(type == AuthorityGroupType.ANONYMOUS)
//      throw new BusinessException(AuthorityGroupErrorCode.CAN_NOT_DELETE_ANONYMOUS_GROUP);
//
//    if(type == AuthorityGroupType.DEFAULT)
//      throw new BusinessException(AuthorityGroupErrorCode.CAN_NOT_DELETE_DEFAULT_GROUP);
//
//    if(type == AuthorityGroupType.SYSTEM)
//      throw new BusinessException(AuthorityGroupErrorCode.CAN_NOT_DELETE_SYSTEM_GROUP);
  }

  @Override
  public String getAuthority() {
    return String.valueOf(type);
  }
}