package com.springcloud.searcher.ingestion;

import com.springcloud.searcher.ingestion.events.PersonUpdatedHandler;
import com.springcloud.searcher.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonLifecycleManager implements PersonUpdatedHandler {

  private final PersonRepository repository;

  @Override
  public void handlePersonUpdate(Person person) {
    Person existingPerson = repository.findById(person.getPersonId());
    if(existingPerson != null) {
      repository.save(
          existingPerson.toBuilder()
              .fullName(person.getFullName())
              .address(person.getAddress())
              .build()
      );
    } else {
      repository.save(person);
    }
  }
}
