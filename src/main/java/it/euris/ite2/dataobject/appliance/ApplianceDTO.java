package it.euris.ite2.dataobject.appliance;

import lombok.Data;

@Data
public class ApplianceDTO {

  String Id;

  String customerId;

  String applianceId;

  String factoryNumber;

  boolean isConnected;

  String connectionDate;

}
