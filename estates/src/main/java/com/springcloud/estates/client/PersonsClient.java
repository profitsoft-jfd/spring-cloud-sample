package com.springcloud.estates.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;
import java.util.Optional;

@FeignClient(name = "persons", decode404 = true)
interface PersonsClient {
  @GetMapping(value = "/persons/{personId}")
  Optional<Map<String, Object>> getPerson(@PathVariable("personId") String personId);
}
