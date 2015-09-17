package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.BreedingEventDto;

public interface BreedingEventService {
    int saveBreedingEventInformation(BreedingEventDto dto) throws Exception;
    
    BreedingEventDto getBreedingEventInformation(Integer breedingEventId) throws PigTraxException;
    
    void deleteBreedingEventInfo(Integer id) throws Exception;
    
    String validateBreedingEvent(BreedingEventDto breedingEventDto);
    
    List<BreedingEventDto> getBreedingEventInformationList(BreedingEventDto breedingEventDto) throws PigTraxException;
    
    List<BreedingEventDto> getActiveBreedingServices(BreedingEventDto breedingEventDto) throws PigTraxException;
    
    BreedingEventDto checkForBreedingServiceId(String pigId, String serviceId, int companyId) throws PigTraxException;
    
    BreedingEventDto getGestationRecord(Integer pigInfoId);
}  
