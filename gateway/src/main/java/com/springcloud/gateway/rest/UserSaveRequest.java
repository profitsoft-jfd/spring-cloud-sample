package com.springcloud.gateway.rest;

import com.springcloud.gateway.model.Role;
import com.springcloud.gateway.model.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserSaveRequest {
  @NotEmpty
  String name;
  @NotEmpty
  String password;
  @NotNull
  Role role;

  User convert() {
    User user = new User();
    user.setRole(role);
    user.setPassword(password);
    user.setName(name);
    return user;
  }
}
