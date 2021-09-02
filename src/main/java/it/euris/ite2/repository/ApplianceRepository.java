package it.euris.ite2.repository;

import it.euris.ite2.entity.Appliance;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApplianceRepository {

  Optional<Appliance> findByApplianceId(String applianceId);

  List<Appliance> findAllByCustomerId(UUID fromString);

  Appliance save(Appliance appliance);

}