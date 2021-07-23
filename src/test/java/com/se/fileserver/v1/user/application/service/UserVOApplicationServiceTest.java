package com.se.fileserver.v1.user.application.service;

import com.se.fileserver.v1.user.domain.model.UserVO;
import com.se.fileserver.v1.user.infra.repository.UserJpaRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserVOApplicationServiceTest {

  @Autowired
  UserJpaRepository userJpaRepository;

  @Autowired
  UserApplicationService userApplicationService;

  @Test
  void findAll() {
    List<UserVO> all = userApplicationService.findAll();
    System.out.println(all.get(0));
  }

  @Test
  void findOne() {
    Optional<UserVO> findOne = userApplicationService.findOne("test");
    System.out.println(findOne.get());
  }
}