package com.springcloud.gateway.persistence;

import com.springcloud.gateway.model.Token;

import java.util.Optional;

public interface TokenRepository {
  Token save(Token user);
  Optional<Token> findById(String id);
  void deleteAll();
}
