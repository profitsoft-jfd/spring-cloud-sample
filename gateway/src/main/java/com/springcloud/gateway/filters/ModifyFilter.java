package com.springcloud.gateway.filters;

import com.springcloud.gateway.model.Role;
import com.springcloud.gateway.model.User;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ModifyFilter implements GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    if (request.getMethod() != HttpMethod.GET) {
      User user = exchange.getAttribute("user");
      if (user == null || user.getRole() != Role.ADMIN) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
      }
    }
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return -2;
  }

}
