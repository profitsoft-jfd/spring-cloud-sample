package com.springcloud.gateway.persistence;

import com.springcloud.gateway.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

interface UserJpaRepository extends UserRepository, JpaRepository<User, Integer> {
}
