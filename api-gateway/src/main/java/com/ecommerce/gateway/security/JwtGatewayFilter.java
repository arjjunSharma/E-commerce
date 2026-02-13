package com.ecommerce.gateway.security;

import java.util.List;

import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GatewayFilter, Ordered {
  @Autowired
  private JwtUtil jwtUtil;
  private static final List<String> PUBLIC_URLS = List.of("/api/users/register", "/api/users/login");

  @Override
  public int getOrder() {
    return -1;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

    String path = exchange.getRequest().getURI().getPath();
    if (PUBLIC_URLS.stream().anyMatch(path::contains)) {
      return chain.filter(exchange);

    }
    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return unauthorized(exchange);
    }

    String token = authHeader.substring(7);
    if (!jwtUtil.validateToken(token)) {
      return unauthorized(exchange);

    }
    String userId = jwtUtil.getUserId(token);
    String role = jwtUtil.getRole(token);
    return chain
        .filter(exchange.mutate().request(req -> req.header("X-User-Id", userId).header("X-User-Role", role)).build());

  }

  private Mono<Void> unauthorized(ServerWebExchange exchange) {
    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
    return exchange.getResponse().setComplete();
  }

}
