package com.se.fileserver.v1.common.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountContextService implements UserDetailsService {

  @Value("${spring.security.anonymous.id}")
  private String ANONYMOUS_ID;

  @Value("${spring.security.anonymous.pw}")
  private String ANONYMOUS_PW;

  @Override
  public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
    Set<GrantedAuthority> temp = new HashSet<>();
    return new User("test", "test", temp);
  }

  public UserDetails loadDefaultGroupAuthorities(String groupName) throws UsernameNotFoundException {
    Set<GrantedAuthority> temp = new HashSet<>();
    return new User("test", "test", temp);
  }
}
