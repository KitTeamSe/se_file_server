package com.se.fileserver.v1.user.application.service;

import static org.junit.jupiter.api.Assertions.*;

import com.se.fileserver.v1.user.infra.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class UserCustomApplicationServiceTest {

  @Autowired
  UserJpaRepository userJpaRepository;

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