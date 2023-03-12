package com.springcloud.persons.persistence;

import com.springcloud.persons.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PersonsMongoRepository extends PersonsRepository, MongoRepository<Person, String> {
}
