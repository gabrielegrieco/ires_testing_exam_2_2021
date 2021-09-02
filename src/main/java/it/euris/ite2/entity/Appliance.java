package it.euris.ite2.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class Appliance extends AbstractEntity {

  UUID customerId;

  String applianceId;

  String factoryNumber;

  boolean isConnected = true;

  LocalDateTime connectionDate;

}
