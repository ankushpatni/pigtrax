package com.pigtrax.pigevents.service.interfaces;

import java.util.List;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PregnancyEventDto;

public interface PregnancyEventService {
   int savePregnancyEventInformation(PregnancyEventDto pregnancyEventDto) throws PigTraxException;
   
   List<PregnancyEventDto> getPregnancyEvents(PregnancyEventDto pregnancyEventDto) throws PigTraxException; 
   
   PregnancyEventDto getPregnancyEventInformation(Integer pregnancyEventId, String language) throws PigTraxException; 
   
   void deletePregnancyEvent(Integer pregnancyEventId) throws PigTraxException;
   
   int validatePregnancyEvent(PregnancyEventDto pregnancyEventDto);
   
   PregnancyEventDto getPregnancyEvent(Integer breedingEventId, String language) throws PigTraxException;
}

