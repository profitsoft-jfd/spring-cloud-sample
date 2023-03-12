package com.springcloud.searcher.ingestion;

import com.springcloud.searcher.ingestion.events.PersonUpdatedHandler;
import com.springcloud.searcher.ingestion.events.PropertyUpdatedHandler;
import com.springcloud.searcher.model.Person;
import com.springcloud.searcher.model.Property;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonLifecycleManager implements PersonUpdatedHandler, PropertyUpdatedHandler {

  private final PersonRepository repository;

  @Override
  public void handlePersonUpdate(Person person) {
    Person existingPerson = repository.findById(person.getPersonId());
    if(existingPerson != null) {
      repository.save(
          existingPerson.toBuilder()
              .fullName(person.getFullName())
              .address(person.getAddress())
              .property(existingPerson.getProperty())
              .build()
      );
    } else {
      repository.save(person);
    }
  }

  @Override
  public void handlePropertyUpdate(Property property, String personId) {
    Person existingPerson = repository.findById(personId);
    if(existingPerson != null) {
      Person oldOwner = repository.findByPropertyId(property.getType(), property.getPropertyId());
      if (oldOwner != null && !oldOwner.getPersonId().equals(existingPerson.getPersonId())) {
        repository.save(
            oldOwner.toBuilder()
                .property(oldOwner.getProperty().stream()
                    .filter(prop -> prop.getType() != property.getType()
                        || !prop.getPropertyId().equals(property.getPropertyId()))
                    .toList()
                )
                .build()
        );
      }
      Optional<Property> existingProperty = Optional.ofNullable(existingPerson.getProperty())
          .flatMap(properties -> properties.stream()
              .filter(prop -> prop.getType() == property.getType()
                  && prop.getPropertyId().equals(property.getPropertyId()))
              .findFirst()
          );
      existingProperty
          .ifPresentOrElse(prop -> {
            prop.setDescription(property.getDescription());
            prop.setDocument(property.getDocument());
          }, () -> {
            if (CollectionUtils.isEmpty(existingPerson.getProperty())) {
              existingPerson.setProperty(List.of(property));
            } else {
              existingPerson.getProperty().add(property);
            }
          });
      repository.save(existingPerson);
    } else {
      log.error("Person for property not found: personId = {}, property = {}", personId, property);
    }
  }
}
