package it.euris.ite2.util;

import it.euris.ite2.dataobject.appliance.ApplianceDTO;
import it.euris.ite2.entity.Appliance;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class ApplianceMapper {

  public ApplianceDTO mapToDto(Appliance appliance) {
    ApplianceDTO applianceDTO = new ApplianceDTO();
    applianceDTO.setId(appliance.getId().toString());
    applianceDTO.setApplianceId(appliance.getApplianceId());
    applianceDTO.setCustomerId(appliance.getCustomerId().toString());
    applianceDTO.setConnected(appliance.isConnected());
    applianceDTO.setConnectionDate(
        appliance.getConnectionDate().format(DateTimeFormatter.ISO_DATE_TIME));
    applianceDTO.setFactoryNumber(appliance.getFactoryNumber());
    return applianceDTO;
  }

  public Appliance mapToEntity(ApplianceDTO applianceDTO) {
    Appliance appliance = new Appliance();
    if (applianceDTO.getId() != null) {
      appliance.setId(UUID.fromString(applianceDTO.getId()));
    }
    if (applianceDTO.getCustomerId() != null) {
      appliance.setCustomerId(UUID.fromString(applianceDTO.getCustomerId()));
    }
    appliance.setApplianceId(applianceDTO.getApplianceId());
    appliance.setConnected(applianceDTO.isConnected());
    if (applianceDTO.getConnectionDate() != null) {
      appliance.setConnectionDate(
          LocalDateTime.parse(applianceDTO.getConnectionDate(), DateTimeFormatter.ISO_DATE_TIME));
    }
    appliance.setFactoryNumber(applianceDTO.getFactoryNumber());
    return appliance;
  }

}