package com.springcloud.persons.service;

import com.springcloud.events.PersonUpdated;
import com.springcloud.persons.model.Person;
import com.springcloud.persons.persistence.PersonsRepository;
import com.springcloud.persons.rest.PersonSaveHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonsManager implements PersonSaveHandler {

  private static final String TOPIC = "person.updated";

  private final PersonsRepository repository;
  private final KafkaOperations<String, PersonUpdated> kafkaOperations;

  @Override
  public Person updatePerson(Person person) {
    Person result = repository.save(person);
    kafkaOperations.send(
        TOPIC,
        result.getPersonId(),
        createPersonUpdated(person)
    );
    return result;
  }

  private PersonUpdated createPersonUpdated(Person person) {
    PersonUpdated result = new PersonUpdated();
    result.setPersonId(person.getPersonId());
    result.setAddress(person.getAddress());
    result.setFullName(person.getFirstName() + " " + person.getLastName());
    return result;
  }
}
