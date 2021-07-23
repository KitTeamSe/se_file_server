package com.se.fileserver.v1.account.application.service;

import com.se.fileserver.v1.account.application.dto.AccountInfoDto;
import com.se.fileserver.v1.account.domain.model.Account;
import com.se.fileserver.v1.account.domain.repository.AccountRepositoryProtocol;
import com.se.fileserver.v1.common.infra.security.provider.JwtTokenResolver;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountSignInService {
  private final JwtTokenResolver jwtTokenResolver;
  private final AccountRepositoryProtocol accountRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public AccountSignInService(
      JwtTokenResolver jwtTokenResolver,
      AccountRepositoryProtocol accountRepository,
      PasswordEncoder passwordEncoder) {
    this.jwtTokenResolver = jwtTokenResolver;
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public String signIn(AccountInfoDto accountInfoMetadata, String ip){
    Account account = accountRepository.findByIdString(accountInfoMetadata.getId()).orElseThrow(/*error*/);
    if(account.isMatch(accountInfoMetadata.getPassword())){
//     throw error
    }
    String token = jwtTokenResolver.createToken(String.valueOf(account.getIdString()));

    return token;
  }
}
