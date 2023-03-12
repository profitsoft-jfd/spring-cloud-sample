package com.springcloud.searcher.ingestion.events;

import com.springcloud.searcher.model.Property;
import com.springcloud.searcher.model.PropertyType;
import com.springcloud.events.PropertyUpdated;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PropertyUpdatedConsumer {

  private static final String TOPIC = "property.updated";

  private final PropertyUpdatedHandler handler;

  @KafkaListener(topics = TOPIC)
  public void listen(PropertyUpdated event) {
    handler.handlePropertyUpdate(
        Property.builder()
            .type(PropertyType.valueOf(event.getType()))
            .propertyId(event.getId())
            .description(event.getDescription())
            .document(event.getDocument())
            .build(),
        event.getOwnerId()
    );
  }

}
