package com.se.fileserver.v1.account.application.service;

import com.se.fileserver.v1.account.application.dto.AccountSignInDto;
import com.se.fileserver.v1.account.domain.model.Account;
import com.se.fileserver.v1.account.domain.repository.AccountRepositoryProtocol;
import com.se.fileserver.v1.common.domain.exception.BadRequestException;
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
  public String signIn(AccountSignInDto accountSignInDto, String ip){
    Account account = accountRepository.findByIdString(accountSignInDto.getId()).orElseThrow(() -> new BadRequestException("Account not found."));
    if(!account.isMatch(passwordEncoder, accountSignInDto.getPassword())){
      throw new BadRequestException("The password is incorrect.");
    }
    String token = jwtTokenResolver.createToken(String.valueOf(account.getIdString()));

    return token;
  }
}
