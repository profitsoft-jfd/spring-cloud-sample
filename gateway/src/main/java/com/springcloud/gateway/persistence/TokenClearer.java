package com.springcloud.gateway.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenClearer implements ApplicationListener<ApplicationReadyEvent> {

  private final TokenRepository tokenRepository;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    tokenRepository.deleteAll();
  }
}
