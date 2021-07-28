package com.se.fileserver.v1.account.domain.service;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface AccountService {
  boolean isMatch(PasswordEncoder passwordEncoder, String rawPassword);
}
