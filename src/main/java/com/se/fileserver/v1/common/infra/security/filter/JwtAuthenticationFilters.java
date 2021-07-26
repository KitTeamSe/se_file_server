package com.se.fileserver.v1.common.infra.security.filter;

import com.se.fileserver.v1.common.infra.security.provider.JwtTokenResolver;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

public class JwtAuthenticationFilters extends GenericFilterBean {

  private final JwtTokenResolver jwtTokenResolver;

  @Autowired
  public JwtAuthenticationFilters(JwtTokenResolver jwtTokenResolver) {
    this.jwtTokenResolver = jwtTokenResolver;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String token = jwtTokenResolver.resolveToken((HttpServletRequest) request);
    Authentication auth = null;

    if (token != null && jwtTokenResolver.validateToken(token)) {
      auth = jwtTokenResolver.getAuthentication(token);
    }

    SecurityContextHolder.getContext().setAuthentication(auth);
    chain.doFilter(request, response);
  }
}

