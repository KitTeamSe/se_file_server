package com.se.fileserver.v1.Account.application.service;

import com.se.fileserver.v1.Account.domain.model.Account;
import com.se.fileserver.v1.Account.domain.repository.AccountRepositoryProtocol;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCreateService {

  private final AccountRepositoryProtocol userJpaRepository;

  @Autowired
  public AccountCreateService(
      AccountRepositoryProtocol userJpaRepository) {
    this.userJpaRepository = userJpaRepository;
  }

  public Optional<Account> findOne(String id){
    return userJpaRepository.findById(id);
  }

  public List<Account> findAll(){
    return userJpaRepository.findAll();
  }
}
