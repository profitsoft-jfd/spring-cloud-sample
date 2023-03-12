package com.springcloud.estates.persistence;

import com.springcloud.estates.model.Estate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface EstatesMongoRepository extends EstatesRepository, MongoRepository<Estate, String> {
}
