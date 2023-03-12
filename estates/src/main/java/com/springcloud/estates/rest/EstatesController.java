package com.springcloud.estates.rest;

import com.springcloud.estates.model.Estate;
import com.springcloud.estates.rest.request.EstateUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/estates")
public class EstatesController {

  private final EstateUpdateHandler saveHandler;

  @PutMapping
  public ResponseEntity<Estate> putEstate(@RequestBody @Valid EstateUpdateRequest request) {
    return ResponseEntity.ok(saveHandler.updateEstate(request.convert()));
  }
}
