package com.se.fileserver.v1.account.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountDatasource {
  @Value("${spring.datasource.admin.id}")
  private String id;
  @Value("${spring.datasource.admin.password}")
  private String password;

  public String getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }
}
