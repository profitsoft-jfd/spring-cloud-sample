package com.springcloud.persons.persistence;

import com.springcloud.persons.model.Person;

import java.util.Optional;

public interface PersonsRepository {
  Optional<Person> findById(String personId);
  Person save(Person person);
}
