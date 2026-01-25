package com.ecommerce.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class CustomGlobalFilter implements GlobalFilter {
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    // Custom filtering logic goes here
    System.out.println("Global Filter : Request Intercepted" + exchange.getRequest().getURI());
    return chain.filter(exchange)
        .then(Mono.fromRunnable(() -> System.out.println("Global Filter : Response Completed")));
  }

}