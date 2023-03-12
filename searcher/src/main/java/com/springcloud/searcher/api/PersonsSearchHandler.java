package com.springcloud.searcher.api;

import com.springcloud.searcher.model.Person;

import java.util.List;

public interface PersonsSearchHandler {
  List<Person> search(String requestString);
}
