package com.se.fileserver.v1.Account.application.service;

import com.se.fileserver.v1.Account.domain.model.Account;
import com.se.fileserver.v1.Account.infra.repository.AccountJpaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountApplicationServiceTest {

  @Autowired
  AccountJpaRepository accountJpaRepository;

  @Autowired
  AccountCreateService accountCreateService;

  @Test
  void findAll() {
    List<Account> all = accountCreateService.findAll();
    System.out.println(all.get(0));
  }

  @Test
  void findOne() {
    Optional<Account> findOne = accountCreateService.findOne("test");
    System.out.println(findOne.get());
  }
}