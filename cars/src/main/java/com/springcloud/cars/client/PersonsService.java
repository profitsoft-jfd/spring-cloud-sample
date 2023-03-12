package com.springcloud.cars.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonsService {

  private final PersonsClient personsClient;

  public boolean isPersonExist(String personId) {
    return personsClient.getPerson(personId).isPresent();
  }
}
