package com.pigtrax.pigevents.service.interfaces;

import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.PigInfoDto;

public interface BreedingEventService {
    public int saveBreedingEventInformation(BreedingEventDto dto) throws Exception;
    
    public BreedingEventDto getBreedingEventInformation(String serviceId, Integer companyId) throws Exception;
    
    public void deleteBreedingEventInfo(Integer id) throws Exception;
    
    int validateBreedingEvent(BreedingEventDto breedingEventDto);
}  
