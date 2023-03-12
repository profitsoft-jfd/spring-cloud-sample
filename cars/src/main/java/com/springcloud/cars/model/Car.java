package com.springcloud.cars.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("cars")
public class Car {
  @Id
  private String number;
  private String mark;
  private String model;
  private String ownerId;
  private String ownerDocument;
}
