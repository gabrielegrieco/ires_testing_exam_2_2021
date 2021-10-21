package it.euris.ite2.controller;

import it.euris.ite2.dataobject.appliance.ApplianceDTO;
import it.euris.ite2.IApplianceService;

public class ApplianceController {

  private final IApplianceService applianceService;

  public ApplianceController(IApplianceService applianceService) {
    this.applianceService = applianceService;
  }

  public ApplianceDTO setConnected(String applianceId) {
    ApplianceDTO appliance = applianceService.getAppliance(applianceId);
    applianceService.updateApplianceConnectionTime(appliance);
    return appliance;
  }

  public ApplianceDTO saveAppliance(ApplianceDTO applianceDTO) {
    ApplianceDTO appliance = applianceService.saveAppliance(applianceDTO);
    applianceService.updateApplianceConnectionTime(appliance);
    return appliance;
  }

}