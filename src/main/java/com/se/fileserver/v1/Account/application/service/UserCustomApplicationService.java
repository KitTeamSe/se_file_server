package com.se.fileserver.v1.Account.application.service;

import com.se.fileserver.v1.Account.domain.model.Account;
import com.se.fileserver.v1.Account.infra.repository.AccountJpaRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserCustomApplicationService implements UserDetailsService {

  private final AccountJpaRepository userRepositoryProtocol;

  @Autowired
  public UserCustomApplicationService(
      AccountJpaRepository userRepositoryProtocol) {
    this.userRepositoryProtocol = userRepositoryProtocol;
  }

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    return userRepositoryProtocol.findById(id).map(this::creadtSpringSecurityUser).orElseThrow(RuntimeException::new);
  }

  public UserDetails loadDefaultGroupAuthorities(String groupName) throws UsernameNotFoundException {
    Set<GrantedAuthority> temp = new HashSet<>();
    return new org.springframework.security.core.userdetails.User("test", "test", temp);
  }

  public org.springframework.security.core.userdetails.User creadtSpringSecurityUser(
      Account account){
    List<GrantedAuthority> grantedAuthority = List.of(new SimpleGrantedAuthority(account.getAuth()));
    return new org.springframework.security.core.userdetails.User(account.getId(), account.getPassword(), grantedAuthority);
  }
}
