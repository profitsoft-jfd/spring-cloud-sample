package com.springcloud.gateway.filters;

import com.springcloud.gateway.model.Token;
import com.springcloud.gateway.model.User;
import com.springcloud.gateway.persistence.TokenRepository;
import com.springcloud.gateway.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Base64Util;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

  private final UserRepository userRepository;
  private final TokenRepository tokenRepository;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    if ("/token".equals(exchange.getRequest().getPath().value())) {
      String username = request.getQueryParams().getFirst("username");
      String password = request.getQueryParams().getFirst("password");
      if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        return exchange.getResponse().setComplete();
      }
      User user = userRepository.findByName(username);
      if (user == null || !password.equals(user.getPassword())) {
        exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
        return exchange.getResponse().setComplete();
      }
      Token token = new Token();
      token.setUserId(user.getId());
      token.setToken(Base64Util.encode(UUID.randomUUID().toString()));
      tokenRepository.save(token);
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.OK);

      byte[] bytes = token.getToken().getBytes(StandardCharsets.UTF_8);

      DataBuffer buffer = response.bufferFactory().wrap(bytes);

      return response.writeWith(Mono.just(buffer));
    }
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return -10;
  }
}
