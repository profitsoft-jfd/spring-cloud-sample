package com.springcloud.searcher.ingestion;

import com.springcloud.searcher.model.Person;

public interface PersonRepository {

  Person findById(String id);

  void save(Person person);

}
