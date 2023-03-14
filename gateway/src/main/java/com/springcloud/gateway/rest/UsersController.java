package com.springcloud.gateway.rest;

import com.springcloud.gateway.model.User;
import com.springcloud.gateway.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

  private final UserRepository repository;

  @PutMapping
  public ResponseEntity<User> saveUser(@RequestBody @Valid UserSaveRequest request) {
    User sameNameUser = repository.findByName(request.getName());
    if (sameNameUser != null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "user with name " + request.getName() + " already exists"
      );
    }
    return ResponseEntity.ok(repository.save(request.convert()));
  }

  @GetMapping("/list")
  public List<User> list() {
    return repository.findAll();
  }
}
