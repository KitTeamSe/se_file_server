package com.se.fileserver.v1.user.application.service;

import com.se.fileserver.v1.user.domain.model.UserVO;
import com.se.fileserver.v1.user.infra.repository.UserJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

  private final UserJpaRepository userRepositoryProtocol;

  @Autowired
  public UserApplicationService(
      UserJpaRepository userRepositoryProtocol) {
    this.userRepositoryProtocol = userRepositoryProtocol;
  }

  public Optional<UserVO> findOne(String id){
    return userRepositoryProtocol.findById(id);
  }

  public List<UserVO> findAll(){
    return userRepositoryProtocol.findAll();
  }
}
