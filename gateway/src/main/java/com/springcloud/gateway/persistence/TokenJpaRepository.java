package com.springcloud.gateway.persistence;

import com.springcloud.gateway.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

interface TokenJpaRepository extends TokenRepository, JpaRepository<Token, String> {
}
