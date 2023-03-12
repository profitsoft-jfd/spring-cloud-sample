package com.springcloud.searcher.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Dynamic;

import java.util.List;

@Data
@Builder(toBuilder = true)
@FieldNameConstants
@Document(
    indexName = "persons",
    createIndex = false,
    dynamic = Dynamic.FALSE
)
public class Person {
  @Id
  String personId;
  String fullName;
  String address;
  List<Property> property;
}
