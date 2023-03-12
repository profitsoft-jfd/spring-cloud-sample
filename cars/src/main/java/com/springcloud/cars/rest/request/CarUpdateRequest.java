package com.springcloud.cars.rest.request;

import com.springcloud.cars.model.Car;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CarUpdateRequest {
  @NotEmpty
  private String number;
  @NotEmpty
  private String mark;
  @NotEmpty
  private String model;
  @NotEmpty
  private String ownerId;
  @NotEmpty
  private String ownerDocument;

  public Car convert() {
    return Car.builder()
        .number(number)
        .mark(mark)
        .model(model)
        .ownerId(ownerId)
        .ownerDocument(ownerDocument)
        .build();
  }
}
