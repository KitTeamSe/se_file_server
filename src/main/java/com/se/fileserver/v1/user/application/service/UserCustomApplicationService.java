package com.se.fileserver.v1.user.application.service;

import com.se.fileserver.v1.user.domain.model.UserVO;
import com.se.fileserver.v1.user.infra.repository.UserJpaRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserCustomApplicationService implements UserDetailsService {

  private final UserJpaRepository userRepositoryProtocol;

  @Autowired
  public UserCustomApplicationService(
      UserJpaRepository userRepositoryProtocol) {
    this.userRepositoryProtocol = userRepositoryProtocol;
  }

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    return userRepositoryProtocol.findById(id).map(this::creadtSpringSecurityUser).orElseThrow(RuntimeException::new);
  }

  public User creadtSpringSecurityUser(UserVO user){
    Set<GrantedAuthority> temp = new HashSet<>();
    return new User(user.getId(), user.getPassword(), temp);
  }
}
