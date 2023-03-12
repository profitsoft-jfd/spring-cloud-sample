package com.springcloud.estates.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document("estates")
public class Estate {
  @Id
  private String number;
  private String address;
  private String ownerId;
  private String ownerDocument;
}
