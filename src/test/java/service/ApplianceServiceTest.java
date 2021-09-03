package service;

import it.euris.ite2.dataobject.appliance.ApplianceDTO;
import it.euris.ite2.entity.Appliance;
import it.euris.ite2.repository.ApplianceRepository;
import it.euris.ite2.service.util.ApplianceMapper;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.internal.matchers.Any;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ApplianceServiceTest implements IApplianceService, ICustomerApplianceService {

private ApplianceMapper applianceMapper;

    private long timeToLive = 1;
    @Mock
    private ApplianceRepository applianceRepositoryMocked;
    @Override
    public List<ApplianceDTO> getCustomerAppliances(UUID customerId) {

        Appliance myApplianceObj = new Appliance();
        List<Appliance> myApplianceMockedList = Mockito.mock(List.class);
        myApplianceMockedList.add(myApplianceObj);
        Mockito.verify(myApplianceMockedList.add(myApplianceObj));

        Mockito.when(applianceRepositoryMocked.findAllByCustomerId(any())).thenReturn(myApplianceMockedList);

        List<Appliance> appliancesToReturn = applianceRepositoryMocked.findAllByCustomerId(any());

        //Mockito.when(appliancesToReturn.forEach(appliance -> appliance.isConnected())).thenReturn(Boolean.TRUE);

        appliancesToReturn.forEach(appliance -> {boolean connected = appliance.isConnected() && !isConnectionDateExpired(appliance);
        appliance.setConnected(connected); });

        assertEquals(myApplianceMockedList.size(), appliancesToReturn.size());
        return appliancesToReturn.stream().map(applianceMapper::mapToDto)
                .collect(Collectors.toList());
    }
    private boolean isConnectionDateExpired(Appliance appliance) {
        LocalDateTime date = appliance.getConnectionDate();
        return LocalDateTime.now().isAfter(date.plusMinutes(timeToLive));
    }

    @Override
    public ApplianceDTO getAppliance(String applianceId) {
        return null;
    }

    @Override
    public ApplianceDTO updateApplianceConnectionTime(ApplianceDTO appliance) {
        return null;
    }

    @Override
    public ApplianceDTO saveAppliance(ApplianceDTO appliance) {
        return null;
    }
}
