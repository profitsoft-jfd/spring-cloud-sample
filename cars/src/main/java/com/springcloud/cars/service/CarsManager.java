package com.springcloud.cars.service;

import com.springcloud.cars.client.PersonsService;
import com.springcloud.cars.model.Car;
import com.springcloud.cars.persistence.CarsRepository;
import com.springcloud.cars.rest.CarUpdateHandler;
import com.springcloud.events.PropertyUpdated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CarsManager implements CarUpdateHandler {

  private static final String TOPIC = "property.updated";
  private static final String TYPE = "CAR";

  private final CarsRepository repository;
  private final PersonsService personsService;
  private final KafkaOperations<String, PropertyUpdated> kafkaOperations;

  @Override
  public Car updateCar(Car car) {
    if (!personsService.isPersonExist(car.getOwnerId())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          String.format("owner with id = %s doesn't exists", car.getOwnerId())
      );
    }
    Car result = repository.save(car);
    kafkaOperations.send(
        TOPIC,
        car.getOwnerId(),
        createPropertyUpdated(car)
    );
    return result;
  }

  private PropertyUpdated createPropertyUpdated(Car car) {
    PropertyUpdated result = new PropertyUpdated();
    result.setType(TYPE);
    result.setId(car.getNumber());
    result.setDescription(car.getMark() + " " + car.getModel() + " " + car.getNumber());
    result.setOwnerId(car.getOwnerId());
    result.setDocument(car.getOwnerDocument());
    return result;
  }
}
