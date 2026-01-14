package com.ecommerce.user.util;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticateFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    final String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      final String token = authHeader.substring(7);
      try {
        jwtUtil.validateToken(token);
        Long userId = jwtUtil.getuserIdFromToken(token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null,
            Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // You can set authentication in the security context here if needed
      } catch (Exception e) {
        SecurityContextHolder.clearContext();
      }
    }
    filterChain.doFilter(request, response);
  }

}
