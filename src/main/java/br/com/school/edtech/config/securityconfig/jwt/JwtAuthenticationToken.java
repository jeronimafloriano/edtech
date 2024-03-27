package br.com.school.edtech.config.securityconfig.jwt;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
  private Object principal;
  private String token;

  public JwtAuthenticationToken(String token) {
    super(null);
    this.token = token;
  }

  public JwtAuthenticationToken(String token, Object principal, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.token = token;
    super.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }
}
