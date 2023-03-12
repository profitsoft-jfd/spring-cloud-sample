package com.springcloud.persons.rest.request;

import com.springcloud.persons.model.Person;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PersonCreateRequest {
  @NotEmpty
  private String personId;
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  private String address;

  public Person convert() {
    return Person.builder()
        .personId(personId)
        .firstName(firstName)
        .lastName(lastName)
        .address(address)
        .build();
  }
}
