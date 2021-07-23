package com.se.fileserver.v1.Account.application.service;

import com.se.fileserver.v1.Account.infra.repository.AccountJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class AccountCustomApplicationServiceTest {

  @Autowired
  AccountJpaRepository accountJpaRepository;

  @Autowired
  UserCustomApplicationService userCustomApplicationService;

  @Test
  void loadUserByUsername() {
    UserDetails userDetails = userCustomApplicationService.loadUserByUsername("test");
    System.out.println(userDetails.getUsername());
    System.out.println(userDetails.getPassword());
    System.out.println(userDetails.getAuthorities());
  }

}