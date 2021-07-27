package com.se.fileserver.v1.account.application.service;

import com.se.fileserver.v1.account.domain.model.Account;
import com.se.fileserver.v1.account.domain.repository.AccountRepositoryProtocol;
import com.se.fileserver.v1.authority.domain.model.AuthorityGroup;
import com.se.fileserver.v1.authority.domain.repository.AuthorityGroupRepositoryProtocol;
import com.se.fileserver.v1.common.domain.exception.BadRequestException;
import com.se.fileserver.v1.common.domain.exception.NotFoundException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AccountContextService implements UserDetailsService {

  private final AccountRepositoryProtocol accountRepositoryProtocol;
  private final AuthorityGroupRepositoryProtocol authorityGroupRepositoryProtocol;

  @Autowired
  public AccountContextService(
      AccountRepositoryProtocol accountRepositoryProtocol,
      AuthorityGroupRepositoryProtocol authorityGroupRepositoryProtocol) {
    this.accountRepositoryProtocol = accountRepositoryProtocol;
    this.authorityGroupRepositoryProtocol = authorityGroupRepositoryProtocol;
  }

  @Value("${spring.security.anonymous.id}")
  private String ANONYMOUS_ID;

  @Value("${spring.security.anonymous.pw}")
  private String ANONYMOUS_PW;

  //TODO : 사용자 도메인 작성 후 권한 검사 기능 구현
  @Override
  public UserDetails loadUserByUsername(String idString) throws UsernameNotFoundException {
    Account account = accountRepositoryProtocol.findByIdString(idString)
        .orElseThrow(() -> new NotFoundException("Account not found."));

    AuthorityGroup grantedAuthorityGroup = authorityGroupRepositoryProtocol.findByAccountIdString(idString)
        .orElseThrow(() -> new NotFoundException("Authority not found."));

    Set<GrantedAuthority> grantedAuthority = new HashSet<>(Collections.singletonList(grantedAuthorityGroup));
    return new User(account.getIdString(), account.getPassword(), grantedAuthority);
  }

  public UserDetails loadDefaultGroupAuthorities(String groupName) throws UsernameNotFoundException {
    Set<GrantedAuthority> temp = new HashSet<>();
    return new User("test", "test", temp);
  }

  public boolean hasAuthority(String authority){
    Set<String> authorities = getContextAuthorityGroup();
    return authorities.contains(authority);
  }

  public Set<String> getContextAuthorityGroup(){
    return AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
  }

  public Account getContextAccount(){
    return accountRepositoryProtocol.findByIdString(getCurrentAccountIdString())
        .orElseThrow(() -> new NotFoundException("Account not found."));
  }

  public String getCurrentAccountIdString(){
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public String getCurrentClientIP(){
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest();
    String ip = request.getHeader("x-forwarded-for");
    return ip != null ? ip : request.getRemoteAddr();
  }
}
