package com.se.fileserver.v1.common.domain.model;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Component
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AccountGenerateEntity extends BaseEntity{

  @CreatedBy
  private Long createdAccountBy;

  @LastModifiedBy
  private Long lastModifiedAccountBy;
}
