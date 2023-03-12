package com.springcloud.estates.rest.request;

import com.springcloud.estates.model.Estate;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class EstateUpdateRequest {
  @NotEmpty
  private String number;
  @NotEmpty
  private String address;
  @NotEmpty
  private String ownerId;
  @NotEmpty
  private String ownerDocument;

  public Estate convert() {
    return Estate.builder()
        .number(number)
        .address(address)
        .ownerId(ownerId)
        .ownerDocument(ownerDocument)
        .build();
  }
}
