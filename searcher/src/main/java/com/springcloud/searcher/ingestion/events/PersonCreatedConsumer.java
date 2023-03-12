package com.springcloud.searcher.ingestion.events;

import com.springcloud.events.PersonUpdated;
import com.springcloud.searcher.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PersonCreatedConsumer {

  private static final String TOPIC = "person.updated";

  private final PersonUpdatedHandler handler;

  @KafkaListener(topics = TOPIC)
  public void listen(PersonUpdated event) {
    handler.handlePersonUpdate(Person.builder()
        .personId(event.getPersonId())
        .address(event.getAddress())
        .fullName(event.getFullName())
        .build()
    );
  }

}
