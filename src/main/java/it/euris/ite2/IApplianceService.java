package it.euris.ite2;

import it.euris.ite2.dataobject.appliance.ApplianceDTO;

public interface IApplianceService {

  ApplianceDTO getAppliance(String applianceId);

  ApplianceDTO updateApplianceConnectionTime(ApplianceDTO appliance);

  ApplianceDTO saveAppliance(ApplianceDTO appliance);

}
