package br.com.school.edtech.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  @Value("${JWT_SECRET}")
  private String JWT_SECRET;

  public String generateToken(String id, String username, HashMap<String, Object> extraClaims) {

    return Jwts.builder()
      .claims(extraClaims)
      .id(id)
      .subject(username)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(60).toMillis()))
      .signWith(Keys.hmacShaKeyFor(JWT_SECRET.getBytes()))
      .compact();
  }

  public String generateToken(int id, String username) {
    HashMap<String, Object> map = new HashMap<>();
    return generateToken(String.valueOf(id), username, map);
  }

  public String generateToke2(UUID id, String username) {
    HashMap<String, Object> map = new HashMap<>();
    return generateToken(String.valueOf(id), username, map);
  }

  public boolean isTokenValid(String token) {
    Date exp = extractClaims(token).getExpiration();
    return exp.after(new Date(System.currentTimeMillis()));
  }

  public String extractId(String token) {
    Claims claims = extractClaims(token);
    return claims.getId();
  }

  public String extractUsername(String token) {
    Claims claims = extractClaims(token);
    return claims.getSubject();
  }

  public SecretKey getSecretKey() {
    return new SecretKeySpec(JWT_SECRET.getBytes(), "HmacSHA256");
  }

  public Claims extractClaims(String token) {
    return Jwts.parser()
      .verifyWith(getSecretKey())
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }
}
