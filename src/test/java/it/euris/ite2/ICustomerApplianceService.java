package it.euris.ite2;

import it.euris.ite2.dataobject.appliance.ApplianceDTO;

import java.util.List;
import java.util.UUID;

public interface ICustomerApplianceService {

  List<ApplianceDTO> getCustomerAppliances(UUID customerId);

}
