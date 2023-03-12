package com.springcloud.searcher.ingestion;

import com.springcloud.searcher.model.Person;
import com.springcloud.searcher.model.PropertyType;

public interface PersonRepository {

  Person findById(String id);

  Person findByPropertyId(PropertyType type, String id);

  void save(Person person);

}
