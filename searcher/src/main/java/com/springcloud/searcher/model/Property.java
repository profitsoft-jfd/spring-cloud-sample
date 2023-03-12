package com.springcloud.searcher.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@Builder(toBuilder = true)
@FieldNameConstants
public class Property {
  String description;
  String document;
  PropertyType type;
}
