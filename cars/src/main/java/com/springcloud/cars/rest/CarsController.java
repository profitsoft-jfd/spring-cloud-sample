package com.springcloud.cars.rest;

import com.springcloud.cars.model.Car;
import com.springcloud.cars.rest.request.CarUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarsController {

  private final CarUpdateHandler saveHandler;

  @PutMapping
  public ResponseEntity<Car> putCar(@RequestBody @Valid CarUpdateRequest request) {
    return ResponseEntity.ok(saveHandler.updateCar(request.convert()));
  }
}
