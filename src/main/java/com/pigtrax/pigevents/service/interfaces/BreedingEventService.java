package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.BreedingEventDto;

public interface BreedingEventService {
    public int saveBreedingEventInformation(BreedingEventDto dto) throws Exception;
    
    public BreedingEventDto getBreedingEventInformation(BreedingEventDto breedingEventDto) throws Exception;
    
    public void deleteBreedingEventInfo(Integer id) throws Exception;
    
    int validateBreedingEvent(BreedingEventDto breedingEventDto);
    
    List<BreedingEventDto> getBreedingEventInformationList(BreedingEventDto breedingEventDto) throws PigTraxException;
    
    BreedingEventDto checkForBreedingServiceId(String pigId, String serviceId, int companyId) throws PigTraxException;
}  
