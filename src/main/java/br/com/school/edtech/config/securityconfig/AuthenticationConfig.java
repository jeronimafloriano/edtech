package br.com.school.edtech.config.securityconfig;

import br.com.school.edtech.config.securityconfig.jwt.JwtAuthenticationProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
  private final JwtAuthenticationProvider jwtAuthenticationProvider;

  @Bean
  public AuthenticationManager authenticationManager() {
    return new ProviderManager(List.of(jwtAuthenticationProvider));
  }
}
