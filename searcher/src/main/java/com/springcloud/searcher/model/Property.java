package com.springcloud.searcher.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@Builder(toBuilder = true)
@FieldNameConstants
public class Property {
  String propertyId;
  String description;
  String document;
  PropertyType type;
}
