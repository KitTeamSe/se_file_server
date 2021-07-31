package com.se.fileserver.v1.authority.infra;

import com.se.fileserver.v1.authority.domain.model.AuthorityGroup;
import com.se.fileserver.v1.authority.domain.model.AuthorityGroupType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthorityDatasource {
  @Value("${spring.datasource.admin.id}")
  private String id;
  private final AuthorityGroup authorityGroup;

  public AuthorityDatasource() {
     authorityGroup = new AuthorityGroup("default", "datasource-default", AuthorityGroupType.SYSTEM);
  }

  public String getId() {
    return id;
  }

  public AuthorityGroup getAuthorityGroup() {
    return authorityGroup;
  }
}
