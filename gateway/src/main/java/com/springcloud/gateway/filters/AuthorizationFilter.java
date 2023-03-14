package com.springcloud.gateway.filters;

import com.springcloud.gateway.model.Token;
import com.springcloud.gateway.model.User;
import com.springcloud.gateway.persistence.TokenRepository;
import com.springcloud.gateway.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter implements GlobalFilter, Ordered {

  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

    if (authHeader == null
        || !authHeader.toLowerCase().startsWith("bearer")) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    String[] authHeaderParts = authHeader.split(" ");
    if (authHeaderParts.length != 2) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    String accessTokenValue = authHeaderParts[1];
    Optional<Token> token = tokenRepository.findById(accessTokenValue);
    if (token.isEmpty()) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    Optional<User> user = userRepository.findById(token.get().getUserId());
    if (user.isEmpty()) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    exchange.getAttributes().put("user", user.get());
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return -5;
  }

}
