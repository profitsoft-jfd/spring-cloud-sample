package com.springcloud.searcher.ingestion.events;

import com.springcloud.searcher.model.Property;

public interface PropertyUpdatedHandler {
  void handlePropertyUpdate(Property property, String personId);
}
