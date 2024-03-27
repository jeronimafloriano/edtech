package br.com.school.edtech.config.securityconfig.jwt;

import br.com.school.edtech.config.auth.JwtService;
import br.com.school.edtech.user.domain.model.Role;
import br.com.school.edtech.user.domain.model.User;
import br.com.school.edtech.user.domain.model.UserId;
import br.com.school.edtech.user.domain.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final JwtService jwtService;
  private final UserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    System.out.println("Jwt Auth Provider");
    String token = (String) authentication.getCredentials();
    if (!jwtService.isTokenValid(token)) {
      System.out.println("Invalid jwt");
      return null;
    }
    String strId = jwtService.extractId(token);
    User user = userRepository.findById(new UserId(UUID.fromString(strId))).orElse(null);
    if (user == null) return null;
    List<Role> roles = Arrays.asList(user.getRole());
    List<GrantedAuthority> authorities = roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.toString()))
        .collect(Collectors.toList());
    return new JwtAuthenticationToken(token, user, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(JwtAuthenticationToken.class);
  }
}
