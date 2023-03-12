package com.springcloud.searcher.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Value
@Builder(toBuilder = true)
@FieldNameConstants
@Document(
    indexName = "persons",
    createIndex = false
)
public class Person {
  @Id
  String personId;
  String fullName;
  String address;
  List<Property> property;
}
