package it.euris.ite2.impl;

import it.euris.ite2.dataobject.appliance.ApplianceDTO;
import it.euris.ite2.entity.AbstractEntity;
import it.euris.ite2.entity.Appliance;
import it.euris.ite2.repository.ApplianceRepository;
import it.euris.ite2.util.ApplianceMapper;
import static org.junit.Assert.assertEquals;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@NoArgsConstructor
public class ApplianceServiceTest  {

    private ApplianceService systemUnderTest;

    @Mock
    ApplianceRepository applianceRepository;

    @Mock
    ApplianceMapper applianceMapper;

    @BeforeEach
    void SetUp (){
        systemUnderTest = new ApplianceService(applianceRepository, applianceMapper, 777L );
    }

    @Test
    @DisplayName("GivenApplianceId_WhenGetAppliance_ThenMockedDtoShouldBeConnectedAndDummyIdShouldBeEqualsToMockedDtoId")
    void GivenApplianceIdWhenGetApplianceThenMockedDtoShouldBeConnectedAndDummyIdShouldBeEqualsToMockedDtoId(){

        //arrange
        String dummyId = "1";
        Appliance mockedAppliance = new Appliance();
        mockedAppliance.setConnected(true);
        mockedAppliance.setConnectionDate(LocalDateTime.now());
        mockedAppliance.setApplianceId(dummyId);
        ApplianceDTO mockedApplianceDTO = new ApplianceDTO();
        mockedApplianceDTO.setConnected(true);
        mockedApplianceDTO.setConnectionDate(LocalDateTime.now().toString());
        mockedApplianceDTO.setApplianceId(dummyId);
        Mockito.when(applianceRepository.findByApplianceId(any())).thenReturn(java.util.Optional.ofNullable(mockedAppliance));
        Mockito.when(applianceMapper.mapToDto(any())).thenReturn(mockedApplianceDTO);

        //act
        ApplianceDTO mockedDto = systemUnderTest.getAppliance(dummyId);

        //assert
        assertTrue(mockedDto.isConnected());
        assertEquals(dummyId, mockedDto.getApplianceId());

    }

    @Test
    @DisplayName("givenApplianceDto_WhenUpdateApplianceConnectionTime_" +
            "ThenSetConnectedMethodShouldBeCall," +
            "MockedApplianceShouldBeConnected" +
            "AndUpdatedApplianceDTOEqualsToMyApplianceDtoToReturn")
    void givenApplianceDtoWhenUpdateApplianceConnectionTimeThenUpdatedApplianceDTOShpldEqualsToMyApplianceDtoToReturn(){

        // arrange
        ApplianceDTO myApplianceDto = Mockito.mock(ApplianceDTO.class);
        Appliance mockedAppliance = Mockito.mock(Appliance.class);
        ApplianceDTO myApplianceDtoToReturn = Mockito.mock(ApplianceDTO.class);

        // act
        Mockito.when(applianceMapper.mapToEntity(myApplianceDto)).thenReturn(mockedAppliance);
        Mockito.when(applianceRepository.save(mockedAppliance)).thenReturn(mockedAppliance);
        Mockito.when(applianceMapper.mapToDto(any())).thenReturn(myApplianceDtoToReturn);
        ApplianceDTO updatedApplianceDTO = systemUnderTest.updateApplianceConnectionTime(myApplianceDto);

        // assert
        Mockito.verify(mockedAppliance, times(1)).setConnectionDate(any());
        Mockito.verify(mockedAppliance, times(1)).setConnected(true);
        assertEquals(updatedApplianceDTO, myApplianceDtoToReturn);
    }

    @Test
    @DisplayName("givenApplianceDto_WhenSaveAppliance_ThenMapToEntityMethodShouldHasCalled" +
            "ApplianceRepositorySavemethodShouldHasCalled" +
            "MapToDtoMethodShouldHasCalled" +
            "mockedApplianceMethodShouldHasCalled" +
            "SavedApplianceDTOShouldEqualsMockedAppliaceDto")
    void givenApplianceDtoWhenSaveApplianceThenSavedApplianceDTOShouldEqualsMockedAppliaceDto(){

        // arrange
        ApplianceDTO mockedAppliaceDto = Mockito.mock(ApplianceDTO.class);
        Appliance mockedAppliance = Mockito.mock(Appliance.class);

        Mockito.when(applianceMapper.mapToEntity(mockedAppliaceDto)).thenReturn(mockedAppliance);
        Mockito.when(applianceRepository.save(any())).thenReturn(mockedAppliance);
        Mockito.when(applianceMapper.mapToDto(mockedAppliance)).thenReturn(mockedAppliaceDto);

        //act
        ApplianceDTO savedApplianceDTO = systemUnderTest.saveAppliance(mockedAppliaceDto);

        //assert
        Mockito.verify(applianceMapper, times(1)).mapToEntity(mockedAppliaceDto);
        Mockito.verify(applianceRepository, times(1)).save(mockedAppliance);
        Mockito.verify(applianceMapper, times(1)).mapToDto(mockedAppliance);
        Mockito.verify(mockedAppliance, times(1)).setModifiedDate(any());
        assertEquals(savedApplianceDTO, mockedAppliaceDto);
    }


    @Test
    @DisplayName("givenCustomerId_WhenGetCustomerAppliance_ThenApplianceListShouldNotEmpty" +
            "ApplianceDtoListContainsApplianceDto")
    void givenApplianceDTOListWhenGetCustomerAppliancesThenShouldReturnedListShouldEqualsToMockedConnectedAppliancesList(){

        //arrange
        UUID id = UUID.randomUUID();
        String id1 = id.toString();
        LocalDateTime l1 = LocalDateTime.MIN;

        List<Appliance> applianceList = new ArrayList<>();
        Appliance appliance = new Appliance();
        applianceList.add(appliance);
        appliance.setApplianceId(id1);
        appliance.setCustomerId(id);
        appliance.setId(id);
        appliance.setConnectionDate(l1);
        appliance.setFactoryNumber("");
        applianceList.add(appliance);

        ApplianceDTO applianceDTO = new ApplianceDTO();
        applianceDTO.setApplianceId(id.toString());
        applianceDTO.setCustomerId(id1);
        applianceDTO.setConnected(true);
        applianceDTO.setId("1");
        applianceDTO.setFactoryNumber("1");
        applianceDTO.setConnectionDate(l1.toString());

        Mockito.when(applianceRepository.findAllByCustomerId(id)).thenReturn(applianceList);
        Mockito.when(applianceMapper.mapToDto(appliance)).thenReturn(applianceDTO);

        //act
        List<ApplianceDTO> applianceDTOList = systemUnderTest.getCustomerAppliances(id);

        // assert
        assertThat(applianceDTOList).isNotEmpty();
        assertThat(applianceDTOList.contains(applianceDTO));


    }
}
