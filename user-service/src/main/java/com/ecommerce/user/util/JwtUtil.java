package com.ecommerce.user.util;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {
  private final SecretKey key;
  private final long jwtExpirationMs;

  // Constructor to initialize the secret key and expiration time

  public JwtUtil(@Value("${app.jwt.secret}") String Secret, @Value("${app.jwt.expiration-ms}") long jwtExpirationMs) {
    this.key = Keys.hmacShaKeyFor(Secret.getBytes());
    this.jwtExpirationMs = jwtExpirationMs;
  }

  // generate JWT token

  public String generateToken(String email, Long userId, String role) {
    long now = System.currentTimeMillis();
    return Jwts.builder()
        .subject(String.valueOf(userId))
        .claim("email", email)
        .claim("role", role)
        .issuedAt(new java.util.Date(now))
        .expiration(new java.util.Date(now + jwtExpirationMs))
        .signWith(key)
        .compact();
  }

  // validate token

  public Jws<Claims> validateToken(String token) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
  }

  // fetching user Id from Token
  public Long getuserIdFromToken(String token) {
    Claims claims = validateToken(token).getPayload();
    return Long.valueOf(claims.getSubject());

  }

}