package com.springcloud.estates.service;

import com.springcloud.estates.client.PersonsService;
import com.springcloud.estates.model.Estate;
import com.springcloud.estates.persistence.EstatesRepository;
import com.springcloud.estates.rest.EstateUpdateHandler;
import com.springcloud.events.PropertyUpdated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EstatesManager implements EstateUpdateHandler {

  private static final String TOPIC = "property.updated";
  private static final String TYPE = "ESTATE";

  private final EstatesRepository repository;
  private final PersonsService personsService;
  private final KafkaOperations<String, PropertyUpdated> kafkaOperations;

  @Override
  public Estate updateEstate(Estate estate) {
    if (!personsService.isPersonExist(estate.getOwnerId())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          String.format("owner with id = %s doesn't exists", estate.getOwnerId())
      );
    }
    Estate result = repository.save(estate);
    kafkaOperations.send(
        TOPIC,
        estate.getOwnerId(),
        createPropertyUpdated(estate)
    );
    return result;
  }

  private PropertyUpdated createPropertyUpdated(Estate car) {
    PropertyUpdated result = new PropertyUpdated();
    result.setType(TYPE);
    result.setId(car.getNumber());
    result.setDescription(car.getAddress() + " " + car.getNumber());
    result.setOwnerId(car.getOwnerId());
    result.setDocument(car.getOwnerDocument());
    return result;
  }
}
