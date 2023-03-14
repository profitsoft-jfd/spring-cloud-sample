package com.springcloud.gateway.persistence;

import com.springcloud.gateway.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  User save(User user);
  User findByName(String name);
  Optional<User> findById(Integer id);
  List<User> findAll();
}
