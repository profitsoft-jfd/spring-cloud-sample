package com.springcloud.searcher.ingestion.events;

import com.springcloud.searcher.model.Person;

public interface PersonUpdatedHandler {
  void handlePersonUpdate(Person person);
}
