package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.MatingDetailsDto;

public interface BreedingEventService {
    BreedingEventDto saveBreedingEventInformation(BreedingEventDto dto) throws Exception;
    
    BreedingEventDto getBreedingEventInformation(Integer breedingEventId) throws PigTraxException;
    
    void deleteBreedingEventInfo(Integer id) throws Exception;
    
    String validateBreedingEvent(MatingDetailsDto matingDetailsDto);
    
    List<BreedingEventDto> getBreedingEventInformationList(BreedingEventDto breedingEventDto) throws PigTraxException;
    
    List<BreedingEventDto> getActiveBreedingServices(BreedingEventDto breedingEventDto) throws PigTraxException;
    
    BreedingEventDto checkForBreedingServiceId(String pigId, int companyId) throws PigTraxException;
    
    
}  
