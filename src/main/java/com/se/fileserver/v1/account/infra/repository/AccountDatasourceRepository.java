package com.se.fileserver.v1.account.infra.repository;

import com.se.fileserver.v1.account.domain.model.Account;
import com.se.fileserver.v1.account.domain.repository.AccountRepositoryProtocol;
import com.se.fileserver.v1.account.infra.Datasource;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class AccountDatasourceRepository implements AccountRepositoryProtocol {

  protected final Datasource datasource;

  @Autowired
  public AccountDatasourceRepository(Datasource datasource) {
    this.datasource = datasource;
  }

  @Override
  public Optional<Account> findByIdString(String id) {
    Account account = null;
    if(id.equals(datasource.getId())){
      account = new Account(datasource.getId(), datasource.getPassword());
    }

    return Optional.ofNullable(account);
  }
}
