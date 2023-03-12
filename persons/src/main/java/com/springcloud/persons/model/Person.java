package com.springcloud.persons.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("persons")
public class Person {
  @Id
  private String personId;
  private String firstName;
  private String lastName;
  private String address;
}
