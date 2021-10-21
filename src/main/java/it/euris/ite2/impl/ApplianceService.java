package it.euris.ite2.impl;

import it.euris.ite2.IApplianceService;
import it.euris.ite2.ICustomerApplianceService;
import it.euris.ite2.dataobject.appliance.ApplianceDTO;
import it.euris.ite2.entity.Appliance;
import it.euris.ite2.exception.EccNotFoundException;
import it.euris.ite2.exception.EccUpsertFailedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import it.euris.ite2.repository.ApplianceRepository;
import it.euris.ite2.util.ApplianceMapper;

public class ApplianceService implements IApplianceService, ICustomerApplianceService {

  private final ApplianceRepository applianceRepository;

  // il mapper sostanzialmente mi trasforma l'oggetto appliance da model a dto a seconda dell' operazione
  private final ApplianceMapper applianceMapper;

  private final long timeToLive;

  // constructor
  public ApplianceService(ApplianceRepository applianceRepository, ApplianceMapper applianceMapper, long timeToLive) {
    this.applianceRepository = applianceRepository;
    this.applianceMapper = applianceMapper;
    this.timeToLive = timeToLive;
  }

  @Override
  public ApplianceDTO getAppliance(String applianceId) {
    Optional<Appliance> applianceFound = applianceRepository.findByApplianceId(applianceId);
    if (applianceFound.isEmpty()) {
      throw new EccNotFoundException(String.format("Appliance not found with id %s", applianceId));
    }
    Appliance appliance = applianceFound.get();
    boolean connected = appliance.isConnected() && !isConnectionDateExpired(appliance);
    appliance.setConnected(connected);
    return applianceMapper.mapToDto(appliance);
  }

  @Override
  public ApplianceDTO updateApplianceConnectionTime(ApplianceDTO applianceDTO) {
    Appliance appliance = applianceMapper.mapToEntity(applianceDTO);
    appliance.setConnectionDate(LocalDateTime.now());
    appliance.setConnected(true);
    appliance = saveAppliance(appliance);
    return applianceMapper.mapToDto(appliance);
  }

  @Override
  public ApplianceDTO saveAppliance(ApplianceDTO applianceDTO) {
    Appliance appliance = applianceMapper.mapToEntity(applianceDTO);
    appliance = saveAppliance(appliance);
    return applianceMapper.mapToDto(appliance);
  }

  private Appliance saveAppliance(Appliance appliance) {
    try {
      appliance.setModifiedDate(LocalDateTime.now());
      return applianceRepository.save(appliance);
    } catch (Exception e) {
      throw new EccUpsertFailedException(String.format("Failed to upsert appliance %s", appliance.getApplianceId()));
    }
  }

  private boolean isConnectionDateExpired(Appliance appliance) {
    LocalDateTime date = appliance.getConnectionDate();
    return LocalDateTime.now().isAfter(date.plusMinutes(timeToLive));
  }

  @Override
  public List<ApplianceDTO> getCustomerAppliances(UUID customerId) {
    List<Appliance> appliances = applianceRepository.findAllByCustomerId(customerId);
    appliances.forEach(appliance -> {
      boolean connected = appliance.isConnected() && !isConnectionDateExpired(appliance);
      appliance.setConnected(connected);
    });
    return appliances.stream()
        .map(applianceMapper::mapToDto)
        .collect(Collectors.toList());
  }
}
