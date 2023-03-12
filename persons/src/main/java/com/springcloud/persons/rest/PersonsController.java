package com.springcloud.persons.rest;

import com.springcloud.persons.model.Person;
import com.springcloud.persons.persistence.PersonsRepository;
import com.springcloud.persons.rest.request.PersonCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonsController {

  private final PersonsRepository repository;
  private final PersonSaveHandler saveHandler;

  @GetMapping(value = "/{id}")
  public ResponseEntity<Person> getPerson(@PathVariable String id) {
    return repository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  @PutMapping
  public ResponseEntity<Person> putPerson(@RequestBody @Valid PersonCreateRequest person) {
    return ResponseEntity.ok(saveHandler.updatePerson(person.convert()));
  }
}
