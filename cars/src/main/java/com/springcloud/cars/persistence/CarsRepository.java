package com.springcloud.cars.persistence;

import com.springcloud.cars.model.Car;

public interface CarsRepository {
  Car save(Car car);
}
