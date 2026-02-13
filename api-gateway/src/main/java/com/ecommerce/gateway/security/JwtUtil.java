package com.ecommerce.gateway.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;
  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());

  }

  public Claims extractClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }

  public boolean validateToken(String token) {
    try {
      extractClaims(token);
      return true;

    } catch (Exception e) {
      return false;

    }

  }

  public String getUserId(String token) {
    return extractClaims(token).getSubject();
  }

  public String getRole(String token) {
    return extractClaims(token).get("role", String.class);

  }

}
