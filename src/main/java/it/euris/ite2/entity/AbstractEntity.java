package it.euris.ite2.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public abstract class AbstractEntity {

  UUID id;

  LocalDateTime createdDate;

  LocalDateTime modifiedDate;

  public AbstractEntity() {
    id = UUID.randomUUID();
    createdDate = LocalDateTime.now();
    modifiedDate = LocalDateTime.now();
  }

}
