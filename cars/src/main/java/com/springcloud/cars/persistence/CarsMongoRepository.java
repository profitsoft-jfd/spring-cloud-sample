package com.springcloud.cars.persistence;

import com.springcloud.cars.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CarsMongoRepository extends CarsRepository, MongoRepository<Car, String> {
}
