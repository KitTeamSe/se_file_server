package com.se.fileserver.v1.authority.infra.repository;

import com.se.fileserver.v1.authority.domain.model.AuthorityGroup;
import com.se.fileserver.v1.authority.domain.model.AuthorityGroupType;
import com.se.fileserver.v1.authority.domain.repository.AuthorityGroupRepositoryProtocol;
import com.se.fileserver.v1.authority.infra.Datasource;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class AuthorityGroupDatasourceRepository implements AuthorityGroupRepositoryProtocol {
  private final Datasource datasource;

  @Autowired
  public AuthorityGroupDatasourceRepository(
      Datasource datasource) {
    this.datasource = datasource;
  }

  @Override
  public Optional<AuthorityGroup> findByAccountIdString(String idString) {
    AuthorityGroup authorityGroup = null;
    if(datasource.getId().equals(idString)){
      authorityGroup = new AuthorityGroup("default", "default", AuthorityGroupType.SYSTEM);
    }
    return Optional.ofNullable(authorityGroup);
  }
}
